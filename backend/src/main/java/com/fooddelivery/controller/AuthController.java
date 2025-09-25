package com.fooddelivery.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fooddelivery.dto.ApiResponse;
import com.fooddelivery.dto.LoginRequest;
import com.fooddelivery.dto.RegisterRequest;
import com.fooddelivery.entity.User;
import com.fooddelivery.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://ec2-98-84-110-225.compute-1.amazonaws.com:3000", allowCredentials = "true")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Map<String, String>>> login(@Valid @RequestBody LoginRequest request) {
        try {
            String token = userService.login(request);
            Map<String, String> data = new HashMap<>();
            data.put("token", token);
            return ResponseEntity.ok(ApiResponse.success("Login successful", data));
        } catch (Exception e) {
            e.printStackTrace(); // Log full stack trace in backend
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Login failed: " + e.getMessage()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Map<String, String>>> register(@Valid @RequestBody RegisterRequest request) {
        try {
            User user = userService.register(request);
            // Auto-login after registration
            String token = userService.login(new LoginRequest(request.getEmail(), request.getPassword()));
            Map<String, String> data = new HashMap<>();
            data.put("token", token);
            return ResponseEntity.ok(ApiResponse.success("Registration successful", data));
        } catch (IllegalArgumentException e) {
            // For validation/business rule failures
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Registration failed: " + e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace(); // Log actual backend error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Server error during registration: " + e.getMessage()));
        }
    }

}
