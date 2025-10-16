package com.acegrade.repository;

import com.acegrade.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Find user by college email
     * @param collegeEmail the college email to search for
     * @return Optional<User> containing the user if found
     */
    Optional<User> findByCollegeEmail(String collegeEmail);
    
    /**
     * Find user by register number
     * @param registerNumber the register number to search for
     * @return Optional<User> containing the user if found
     */
    Optional<User> findByRegisterNumber(String registerNumber);
    
    /**
     * Check if user exists by college email
     * @param collegeEmail the college email to check
     * @return true if user exists, false otherwise
     */
    boolean existsByCollegeEmail(String collegeEmail);
    
    /**
     * Check if user exists by register number
     * @param registerNumber the register number to check
     * @return true if user exists, false otherwise
     */
    boolean existsByRegisterNumber(String registerNumber);
    
    /**
     * Find user by college email and password for login authentication
     * @param collegeEmail the college email
     * @param password the password
     * @return Optional<User> containing the user if credentials match
     */
    Optional<User> findByCollegeEmailAndPassword(String collegeEmail, String password);
}
