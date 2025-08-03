package com.skillSphere.UserService.Utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skillSphere.UserService.Response.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Helper {

    public void writeErrorResponse(HttpServletResponse response, ApiResponse<?> apiResponse) throws IOException {
        response.setStatus(apiResponse.getStatusCode());
        response.setContentType("application/json");

        ObjectMapper mapper = new ObjectMapper();
        String responseBody = mapper.writeValueAsString(apiResponse);

        response.getWriter().write(responseBody);
    }
}
