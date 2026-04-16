package com.duoc.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    JWTAuthenticationConfig jwtAuthenticationConfig;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        try {
            if (!userService.authenticate(loginRequest.getUsername(), loginRequest.getPassword())) {
                return ResponseEntity.badRequest().body("Invalid credentials");
            }

            String token = jwtAuthenticationConfig.getJWTToken(loginRequest.getUsername());
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Login failed: " + e.getMessage());
        }
    }
}