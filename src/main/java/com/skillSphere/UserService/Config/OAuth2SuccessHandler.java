package com.skillSphere.UserService.Config;


import com.skillSphere.UserService.Dto.enums.UserRole;
import com.skillSphere.UserService.Entity.UserEntity;
import com.skillSphere.UserService.Repository.UserRepository;
import com.skillSphere.UserService.Utils.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public  class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepo;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
            throws IOException, ServletException {

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String firstName = oAuth2User.getAttribute("firstname");
        String lastName = oAuth2User.getAttribute("lastname");
        String email = oAuth2User.getAttribute("email");
        UserRole role = oAuth2User.getAttribute("role");

        UserEntity user = userRepo.findByEmailIgnoreCase(email)
                .orElseGet(() -> userRepo.save(new UserEntity(null,firstName,lastName,email,"OAUTH2_USER",null, role,null,null,null)));

        String jwt = jwtUtil.generateToken(new User(user.getEmail(), "OAUTH2_USER", List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()))));
        response.sendRedirect("http://localhost:3000/oauth-success?token=" + jwt);
    }
}

