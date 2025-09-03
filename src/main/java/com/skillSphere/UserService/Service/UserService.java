package com.skillSphere.UserService.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillSphere.UserService.Dto.UserSummaryDto;
import com.skillSphere.UserService.Entity.UserEntity;
import com.skillSphere.UserService.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService implements UserDetailsService {

    @NotNull
    @Autowired
    private UserRepository userRepo;
    private final RedisTemplate<String, Object> redisTemplate;
    public UserService(UserRepository userRepo, RedisTemplate<String, Object> redisTemplate) {
        this.userRepo = userRepo;
        this.redisTemplate = redisTemplate;
    }

    public UserEntity getUserById(Long userId) {
        log.info("Retrieving User details by id: {}", userId);
        UserEntity user=  userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id " + userId));

        return user;
    }

    public List<UserSummaryDto> getAll() {

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

    public List<UserSummaryDto> getUsersWithCount(int page, int pageSize, int count){

       // redisTemplate.getConnectionFactory().getConnection().flushDb();

        String cacheKey = "users:page=" + page + ":size=" + pageSize + ":count=" + count;

        List<Object> cached = redisTemplate.opsForList().range(cacheKey, 0, -1);
        if (cached != null && !cached.isEmpty()) {
            List<UserSummaryDto> cachedUsers = redisTemplate.opsForList()
                    .range(cacheKey, 0, -1)
                    .stream()
                    .map(obj -> (UserSummaryDto) obj)
                    .collect(Collectors.toList());
            System.out.println("✅ Returning from Redis Cache");
            return cachedUsers;
        }

        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("ratings").descending());
        Page<UserSummaryDto> usersPage = this.userRepo.findAllProjectedBy(pageable);
        List<UserSummaryDto> users=  usersPage.getContent()
                .stream()
                .limit(count)
                .toList();
        // Save in cache for next time
        redisTemplate.opsForList().rightPushAll(cacheKey,  new ArrayList<>(users));
        redisTemplate.expire(cacheKey, Duration.ofMinutes(5));
        return users;
    }
}
