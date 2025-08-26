package com.skillSphere.UserService.Entity;

import com.skillSphere.UserService.Dto.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class RequestEntity {
    public static class LoginRequest {

        private String email;
        private String password;

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

    public static class RegisterRequest {
        @Getter
        @Setter
        private String firstName;
        @Getter
        @Setter
        private String lastName;
        @Getter
        @Setter
        private String email;
        @Getter
        @Setter
        private String password;
        @Getter
        @Setter
        private UserRole role;
        @Getter
        @Setter
        private String phoneNumber;
        @Getter
        @Setter
        private String description;
        @Getter
        @Setter
        private List<Long> mentorshipSkills;
        @Getter
        @Setter
        private  List<Long> learnerSkills;

    }
}
