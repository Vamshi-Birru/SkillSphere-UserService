package com.skillSphere.UserService.Service;

import com.skillSphere.UserService.Entity.UserEntity;
import com.skillSphere.UserService.Projection.UserSummary;
import com.skillSphere.UserService.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Slf4j
@Service
public class UserService implements UserDetailsService {

    @NotNull
    @Autowired
    private  UserRepository userRepo;

    public UserEntity getUserById(Long userId) {
        log.info("Retrieving User details by id: {}", userId);
        UserEntity user=  userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + userId));

        return user;
    }

    public List<UserSummary> getAll() {

        return userRepo.findAllProjectedBy().stream()
                .toList();
    }


    public UserDetails getUserByEmail(String username) throws UsernameNotFoundException {
        UserEntity user=  this.userRepo.findByEmailIgnoreCase(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found with name " + username));

        return  User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .build();

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUserByEmail(username);
    }

    public List<UserSummary> getUsersWithCount(int page, int pageSize,  int count){
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("ratings").descending());
        Page<UserSummary> usersPage = this.userRepo.findAllProjectedBy(pageable);
        return usersPage.getContent()
                .stream()
                .limit(count)
                .toList();
    }
}
