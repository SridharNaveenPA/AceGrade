package com.acegrade.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "first_name", length = 50)
    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name must not exceed 50 characters")
    private String firstName;
    
    @Column(name = "last_name", length = 50)
    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name must not exceed 50 characters")
    private String lastName;
    
    @Column(name = "register_number", length = 20, unique = true)
    @NotBlank(message = "Register number is required")
    @Size(max = 20, message = "Register number must not exceed 20 characters")
    private String registerNumber;
    
    @Column(name = "department", length = 50)
    @NotBlank(message = "Department is required")
    @Size(max = 50, message = "Department must not exceed 50 characters")
    private String department;
    
    @Column(name = "college_email", length = 100, unique = true)
    @NotBlank(message = "College email is required")
    @Email(message = "Please provide a valid email address")
    @Size(max = 100, message = "College email must not exceed 100 characters")
    private String collegeEmail;
    
    @Column(name = "password", length = 255)
    @NotBlank(message = "Password is required")
    @Size(max = 255, message = "Password must not exceed 255 characters")
    private String password;
    
    // Default constructor
    public User() {}
    
    // Constructor with all fields
    public User(String firstName, String lastName, String registerNumber, 
                String department, String collegeEmail, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.registerNumber = registerNumber;
        this.department = department;
        this.collegeEmail = collegeEmail;
        this.password = password;
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
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", registerNumber='" + registerNumber + '\'' +
                ", department='" + department + '\'' +
                ", collegeEmail='" + collegeEmail + '\'' +
                '}';
    }
}
