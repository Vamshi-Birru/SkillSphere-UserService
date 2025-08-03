package com.skillSphere.UserService.Dto;

import com.skillSphere.UserService.Dto.enums.UserRole;
import lombok.Data;

@Data
public class RegisterRequestDto {
    private String username;
    private String email;
    private String password;
    private UserRole role;
}