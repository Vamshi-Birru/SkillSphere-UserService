package com.skillSphere.UserService.Dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.skillSphere.UserService.Dto.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("firstname")
    private String firstName;

    @JsonProperty("lastname")
    private String lastName;

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;

    @JsonProperty("description")
    private String description;

    @JsonProperty("role")
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @JsonProperty("phonenumber")
    private String phoneNumber;

    @JsonProperty("mentorshipskills")
    private List<Long> mentorshipSkills;

    @JsonProperty("learnerskills")
    private List<Long> learnerSkills;

    @JsonProperty("ratings")
    private double ratings;

}
