package com.skillSphere.UserService.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T>  {
        private String status;
        private int statusCode;
        private T data;
        private String message;

}
