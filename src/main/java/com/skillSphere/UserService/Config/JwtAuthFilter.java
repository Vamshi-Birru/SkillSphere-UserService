package com.skillSphere.UserService.Config;

import com.skillSphere.UserService.Response.ApiResponse;
import com.skillSphere.UserService.Service.UserService;
import com.skillSphere.UserService.Utils.JwtUtil;
import com.skillSphere.UserService.Utils.Helper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserService userDetailsService;
   private final Helper helper;
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getServletPath();
        // Skip JWT check for auth and oauth2 endpoints
        if (path.startsWith("/api/auth/") || path.startsWith("/oauth2/")) {
            filterChain.doFilter(request, response);
            return;
        }
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            String email;
            try {
                 email = jwtUtil.extractEmail(token);
            } catch (Exception e) {
                ApiResponse<?> apiResponse = new ApiResponse<>("error", 401, null, "Invalid or expired token");
                helper.writeErrorResponse(response, apiResponse);
                return;
            }


            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.getUserByEmail(email);
                if (jwtUtil.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }
        else{
            ApiResponse<?> apiResponse = new ApiResponse<>("error", 401, null, "Missing Authorization token");
            helper.writeErrorResponse(response, apiResponse);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
