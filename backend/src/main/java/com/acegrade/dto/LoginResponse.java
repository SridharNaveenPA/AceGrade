package com.acegrade.dto;

public class LoginResponse {
    
    private boolean success;
    private String message;
    private UserInfo userInfo;
    
    // Default constructor
    public LoginResponse() {}
    
    // Constructor for successful login
    public LoginResponse(boolean success, String message, UserInfo userInfo) {
        this.success = success;
        this.message = message;
        this.userInfo = userInfo;
    }
    
    // Constructor for failed login
    public LoginResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
    
    // Getters and Setters
    public boolean isSuccess() {
        return success;
    }
    
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public UserInfo getUserInfo() {
        return userInfo;
    }
    
    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
    
    // Inner class for user information
    public static class UserInfo {
        private Long id;
        private String firstName;
        private String lastName;
        private String registerNumber;
        private String department;
        private String collegeEmail;
        
        // Default constructor
        public UserInfo() {}
        
        // Constructor with all fields
        public UserInfo(Long id, String firstName, String lastName, 
                       String registerNumber, String department, String collegeEmail) {
            this.id = id;
            this.firstName = firstName;
            this.lastName = lastName;
            this.registerNumber = registerNumber;
            this.department = department;
            this.collegeEmail = collegeEmail;
        }
        
        // Getters and Setters
        public Long getId() {
            return id;
        }
        
        public void setId(Long id) {
            this.id = id;
        }
        
        public String getFirstName() {
            return firstName;
        }
        
        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }
        
        public String getLastName() {
            return lastName;
        }
        
        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
        
        public String getRegisterNumber() {
            return registerNumber;
        }
        
        public void setRegisterNumber(String registerNumber) {
            this.registerNumber = registerNumber;
        }
        
        public String getDepartment() {
            return department;
        }
        
        public void setDepartment(String department) {
            this.department = department;
        }
        
        public String getCollegeEmail() {
            return collegeEmail;
        }
        
        public void setCollegeEmail(String collegeEmail) {
            this.collegeEmail = collegeEmail;
        }
        
        @Override
        public String toString() {
            return "UserInfo{" +
                    "id=" + id +
                    ", firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", registerNumber='" + registerNumber + '\'' +
                    ", department='" + department + '\'' +
                    ", collegeEmail='" + collegeEmail + '\'' +
                    '}';
        }
    }
    
    @Override
    public String toString() {
        return "LoginResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", userInfo=" + userInfo +
                '}';
    }
}
