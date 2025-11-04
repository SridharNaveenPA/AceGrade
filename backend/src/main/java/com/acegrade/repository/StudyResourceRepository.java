


package com.acegrade.repository;

import com.acegrade.entity.StudyResource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudyResourceRepository extends JpaRepository<StudyResource, Long> {
    
    /**
     * Find study resources by semester, department, and regulation
     */
    List<StudyResource> findBySemesterAndDepartmentAndRegulation(Integer semester, String department, String regulation);
    
    /**
     * Find study resources by subject
     */
    List<StudyResource> findBySubjectId(Long subjectId);
    
    /**
     * Find study resources by contributor name
     */
    List<StudyResource> findByContributorNameContainingIgnoreCase(String contributorName);
    
    /**
     * Find study resources by description containing (case insensitive)
     */
    @Query("SELECT sr FROM StudyResource sr WHERE LOWER(sr.description) LIKE LOWER(CONCAT('%', :description, '%'))")
    List<StudyResource> findByDescriptionContainingIgnoreCase(@Param("description") String description);
    
    /**
     * Find study resources by semester, department, regulation, and subject name
     */
    @Query("SELECT sr FROM StudyResource sr JOIN sr.subject s WHERE sr.semester = :semester AND sr.department = :department AND sr.regulation = :regulation AND s.name = :subjectName")
    List<StudyResource> findBySemesterAndDepartmentAndRegulationAndSubjectName(
        @Param("semester") Integer semester, 
        @Param("department") String department, 
        @Param("regulation") String regulation, 
        @Param("subjectName") String subjectName);
    
    /**
     * Search study resources by multiple criteria
     */
    @Query("SELECT sr FROM StudyResource sr JOIN sr.subject s WHERE " +
           "(:semester IS NULL OR sr.semester = :semester) AND " +
           "(:department IS NULL OR sr.department = :department) AND " +
           "(:regulation IS NULL OR sr.regulation = :regulation) AND " +
           "(:subjectName IS NULL OR s.name = :subjectName) AND " +
           "(:searchTerm IS NULL OR LOWER(sr.contributorName) LIKE LOWER(:searchTerm) OR " +
           "LOWER(sr.description) LIKE LOWER(:searchTerm) OR " +
           "LOWER(s.name) LIKE LOWER(:searchTerm))")
    List<StudyResource> searchStudyResources(
        @Param("semester") Integer semester,
        @Param("department") String department,
        @Param("regulation") String regulation,
        @Param("subjectName") String subjectName,
        @Param("searchTerm") String searchTerm);
    
    /**
     * Find study resources by uploaded user
     */
    List<StudyResource> findByUploadedById(Long userId);
    
    /**
     * Get most downloaded study resources
     */
    @Query("SELECT sr FROM StudyResource sr ORDER BY sr.downloadCount DESC")
    List<StudyResource> findMostDownloaded();
    
    /**
     * Get study resources ordered by creation date (newest first)
     */
    @Query("SELECT sr FROM StudyResource sr ORDER BY sr.createdAt DESC")
    List<StudyResource> findLatestResources();
    
    /**
     * Count study resources by semester, department, and regulation
     */
    long countBySemesterAndDepartmentAndRegulation(Integer semester, String department, String regulation);
    
    /**
     * Count study resources by subject
     */
    long countBySubjectId(Long subjectId);
    
    /**
     * Simple search by basic filters only (without complex search term)
     */
    @Query("SELECT sr FROM StudyResource sr JOIN sr.subject s WHERE " +
           "(:semester IS NULL OR sr.semester = :semester) AND " +
           "(:department IS NULL OR sr.department = :department) AND " +
           "(:regulation IS NULL OR sr.regulation = :regulation) AND " +
           "(:subjectName IS NULL OR s.name = :subjectName)")
    List<StudyResource> findByBasicFilters(
        @Param("semester") Integer semester,
        @Param("department") String department,
        @Param("regulation") String regulation,
        @Param("subjectName") String subjectName);

    /**
     * Simple search for study resources only (exclude qpapers by filePath prefix)
     */
    @Query("SELECT sr FROM StudyResource sr JOIN sr.subject s WHERE " +
           "(sr.filePath NOT LIKE '/uploads/qpaper%') AND " +
           "(:semester IS NULL OR sr.semester = :semester) AND " +
           "(:department IS NULL OR sr.department = :department) AND " +
           "(:regulation IS NULL OR sr.regulation = :regulation) AND " +
           "(:subjectName IS NULL OR s.name = :subjectName)")
    List<StudyResource> findNonQpaperByBasicFilters(
        @Param("semester") Integer semester,
        @Param("department") String department,
        @Param("regulation") String regulation,
        @Param("subjectName") String subjectName);

    /**
     * Complex search for study resources only (exclude qpapers by filePath prefix)
     */
    @Query("SELECT sr FROM StudyResource sr JOIN sr.subject s WHERE " +
           "(sr.filePath NOT LIKE '/uploads/qpaper%') AND " +
           "(:semester IS NULL OR sr.semester = :semester) AND " +
           "(:department IS NULL OR sr.department = :department) AND " +
           "(:regulation IS NULL OR sr.regulation = :regulation) AND " +
           "(:subjectName IS NULL OR s.name = :subjectName) AND " +
           "(:searchTerm IS NULL OR LOWER(sr.contributorName) LIKE LOWER(:searchTerm) OR " +
           "LOWER(sr.description) LIKE LOWER(:searchTerm) OR " +
           "LOWER(s.name) LIKE LOWER(:searchTerm))")
    List<StudyResource> searchNonQpapers(
        @Param("semester") Integer semester,
        @Param("department") String department,
        @Param("regulation") String regulation,
        @Param("subjectName") String subjectName,
        @Param("searchTerm") String searchTerm);

    /**
     * Simple search limited to question papers only (by filePath prefix)
     */
    @Query("SELECT sr FROM StudyResource sr JOIN sr.subject s WHERE " +
           "sr.filePath LIKE '/uploads/qpaper%' AND " +
           "(:semester IS NULL OR sr.semester = :semester) AND " +
           "(:department IS NULL OR sr.department = :department) AND " +
           "(:regulation IS NULL OR sr.regulation = :regulation) AND " +
           "(:subjectName IS NULL OR s.name = :subjectName)")
    List<StudyResource> findQpapersByBasicFilters(
        @Param("semester") Integer semester,
        @Param("department") String department,
        @Param("regulation") String regulation,
        @Param("subjectName") String subjectName);

    /**
     * Complex search limited to question papers only (by filePath prefix)
     */
    @Query("SELECT sr FROM StudyResource sr JOIN sr.subject s WHERE " +
           "sr.filePath LIKE '/uploads/qpaper%' AND " +
           "(:semester IS NULL OR sr.semester = :semester) AND " +
           "(:department IS NULL OR sr.department = :department) AND " +
           "(:regulation IS NULL OR sr.regulation = :regulation) AND " +
           "(:subjectName IS NULL OR s.name = :subjectName) AND " +
           "(:searchTerm IS NULL OR LOWER(sr.contributorName) LIKE LOWER(:searchTerm) OR " +
           "LOWER(sr.description) LIKE LOWER(:searchTerm) OR " +
           "LOWER(s.name) LIKE LOWER(:searchTerm))")
    List<StudyResource> searchQpapers(
        @Param("semester") Integer semester,
        @Param("department") String department,
        @Param("regulation") String regulation,
        @Param("subjectName") String subjectName,
        @Param("searchTerm") String searchTerm);
}
