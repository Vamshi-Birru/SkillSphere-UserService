package com.skillSphere.UserService.Dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSummaryDto {
    private String email;
    private String firstName;
    private String description;
    private Double ratings;
    
}
