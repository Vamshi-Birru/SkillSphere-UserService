package com.skillSphere.UserService.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.skillSphere.UserService.Dto.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USERS")
public class UserEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 100)
    private Long id;

    @Column(name = "firstname", nullable = false, length = 100)
    private String firstName;

    @Column(name = "lastname", nullable = false, length = 100)
    private String lastName;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "description", nullable = true, length = 1024)
    private String description;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(name = "phone_number", nullable = true, length = 10)
    private String phoneNumber;


    @Column(name = "mentorship_skills", nullable = true)
    private List<Long> mentorshipSkills;


    @Column(name = "learner_skills", nullable = true)
    private List<Long> learnerSkills;
}
