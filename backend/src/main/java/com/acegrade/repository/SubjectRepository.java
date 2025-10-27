package com.acegrade.repository;

import com.acegrade.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    
    /**
     * Find subjects by semester, department, and regulation
     */
    List<Subject> findBySemesterAndDepartmentAndRegulation(Integer semester, String department, String regulation);
    
    /**
     * Find subjects by department and regulation
     */
    List<Subject> findByDepartmentAndRegulation(String department, String regulation);
    
    /**
     * Find subjects by regulation
     */
    List<Subject> findByRegulation(String regulation);
    
    /**
     * Find subject by name, semester, department, and regulation
     */
    Optional<Subject> findByNameAndSemesterAndDepartmentAndRegulation(
        String name, Integer semester, String department, String regulation);
    
    /**
     * Check if subject exists by name, semester, department, and regulation
     */
    boolean existsByNameAndSemesterAndDepartmentAndRegulation(
        String name, Integer semester, String department, String regulation);
    
    /**
     * Find subjects by name containing (case insensitive)
     */
    @Query("SELECT s FROM Subject s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Subject> findByNameContainingIgnoreCase(@Param("name") String name);
    
    /**
     * Get all unique departments
     */
    @Query("SELECT DISTINCT s.department FROM Subject s ORDER BY s.department")
    List<String> findDistinctDepartments();
    
    /**
     * Get all unique regulations
     */
    @Query("SELECT DISTINCT s.regulation FROM Subject s ORDER BY s.regulation")
    List<String> findDistinctRegulations();
    
    /**
     * Get all unique semesters for a given department and regulation
     */
    @Query("SELECT DISTINCT s.semester FROM Subject s WHERE s.department = :department AND s.regulation = :regulation ORDER BY s.semester")
    List<Integer> findDistinctSemestersByDepartmentAndRegulation(@Param("department") String department, @Param("regulation") String regulation);
}
