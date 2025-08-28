package com.skillSphere.UserService.Service;

import com.skillSphere.UserService.Entity.AuthResponse;
import com.skillSphere.UserService.Entity.RequestEntity;
import com.skillSphere.UserService.Entity.UserEntity;
import com.skillSphere.UserService.Repository.UserRepository;
import com.skillSphere.UserService.Utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authManager;

    public AuthResponse register(RequestEntity.RegisterRequest req) {
        UserEntity user = new UserEntity(null,req.getFirstName(), req.getLastName(), req.getEmail(), passwordEncoder.encode(req.getPassword()),req.getDescription(), req.getRole(),req.getPhoneNumber(),req.getMentorshipSkills(),req.getLearnerSkills(),req.getRatings());
        userRepo.save(user);
        String token = jwtUtil.generateToken(
                new User(req.getEmail(), req.getPassword(),  List.of(new SimpleGrantedAuthority("ROLE_" + req.getRole())))
        );
        return new AuthResponse(token);
    }
    public AuthResponse login(RequestEntity.LoginRequest req) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
        UserEntity user = userRepo.findByEmailIgnoreCase(req.getEmail()).orElseThrow();
        String token = jwtUtil.generateToken((UserDetails) auth.getPrincipal());
        return new AuthResponse(token);
    }
}
