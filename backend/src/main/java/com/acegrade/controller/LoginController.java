package com.acegrade.controller;

import com.acegrade.dto.LoginRequest;
import com.acegrade.dto.LoginResponse;
import com.acegrade.entity.User;
import com.acegrade.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:5500", "http://localhost:5500", "file://", "*"})
public class LoginController {
    
    @Autowired
    private UserRepository userRepository;
    
    /**
     * Login endpoint to authenticate users
     * @param loginRequest containing college email and password
     * @return ResponseEntity with login response
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            // Find user by college email and password
            Optional<User> userOptional = userRepository.findByCollegeEmailAndPassword(
                loginRequest.getCollegeEmail(), 
                loginRequest.getPassword()
            );
            
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                
                // Create user info for response
                LoginResponse.UserInfo userInfo = new LoginResponse.UserInfo(
                    user.getId(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getRegisterNumber(),
                    user.getDepartment(),
                    user.getCollegeEmail()
                );
                
                // Return successful login response
                LoginResponse response = new LoginResponse(
                    true, 
                    "Login successful!", 
                    userInfo
                );
                
                return ResponseEntity.ok(response);
            } else {
                // Return failed login response
                LoginResponse response = new LoginResponse(
                    false, 
                    "Invalid email or password. Please check your credentials and try again."
                );
                
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
            
        } catch (Exception e) {
            // Handle any unexpected errors
            LoginResponse response = new LoginResponse(
                false, 
                "An error occurred during login. Please try again later."
            );
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
    /**
     * Health check endpoint
     * @return ResponseEntity with status
     */
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Login service is running!");
    }
}
