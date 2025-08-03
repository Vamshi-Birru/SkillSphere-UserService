package com.skillSphere.UserService.ExceptionHandler;


import com.skillSphere.UserService.Response.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleEntityNotFound(EntityNotFoundException ex) {
        return new ResponseEntity<>(
                new ApiResponse<>("error", 404, null, ex.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<ApiResponse<?>> handleDatabaseError(DataAccessException ex) {
        return new ResponseEntity<>(
                new ApiResponse<>("error", 500, null, "Database error occurred. Please try again later."),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleGeneralError(Exception ex) {
        ex.printStackTrace(); // log stack trace (you can use a logger)
        return new ResponseEntity<>(
                new ApiResponse<>("error", 500, null, "An unexpected error occurred."),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }


}
