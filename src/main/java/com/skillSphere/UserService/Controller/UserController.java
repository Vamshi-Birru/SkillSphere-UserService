package com.skillSphere.UserService.Controller;

import com.skillSphere.UserService.Dto.UserSummaryDto;
import com.skillSphere.UserService.Entity.UserEntity;
import com.skillSphere.UserService.Response.ApiResponse;
import com.skillSphere.UserService.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import io.micrometer.core.annotation.Timed;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/userService/user")
public class UserController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    @Transactional
    @Timed(value = "get.user.timer", description = "Time to get user")
    public ResponseEntity<ApiResponse<?>> getUserById(@PathVariable Long userId) {

        UserEntity user = userService.getUserById(userId);
        return ResponseEntity.ok( new ApiResponse<>("success", 200, user, "User fetched successfully"));

    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<?>> getAllUsers(){

        List<UserSummaryDto> users = userService.getAll();
        Map<String, Object> data = new HashMap<>();
        data.put("users", users);
        return ResponseEntity.ok(new ApiResponse<>("success", 200, data, "Users fetched successfully"));
    }

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<?>> getUsersWithCount(@RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int pageSize,
                                                            @RequestParam(defaultValue = "5") int count){
        System.out.println("count: " + count);
        List<UserSummaryDto> users = userService.getUsersWithCount(page,pageSize, count);
        Map<String,Object> data = new HashMap<>();
        data.put("users", users);
        return ResponseEntity.ok(new ApiResponse<>("success", 200, data, "Users fetched successfully"));
    }
}
