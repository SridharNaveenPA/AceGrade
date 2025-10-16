package com.acegrade.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
    
    @NotBlank(message = "College email is required")
    @Email(message = "Please provide a valid email address")
    private String collegeEmail;
    
    @NotBlank(message = "Password is required")
    private String password;
    
    // Default constructor
    public LoginRequest() {}
    
    // Constructor with parameters
    public LoginRequest(String collegeEmail, String password) {
        this.collegeEmail = collegeEmail;
        this.password = password;
    }
    
    // Getters and Setters
    public String getCollegeEmail() {
        return collegeEmail;
    }
    
    public void setCollegeEmail(String collegeEmail) {
        this.collegeEmail = collegeEmail;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    @Override
    public String toString() {
        return "LoginRequest{" +
                "collegeEmail='" + collegeEmail + '\'' +
                '}';
    }
}
