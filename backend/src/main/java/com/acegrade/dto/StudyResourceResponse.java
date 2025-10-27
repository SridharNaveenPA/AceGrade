package com.acegrade.dto;

import java.time.LocalDateTime;

public class StudyResourceResponse {
    private Long id;
    private String subjectName;
    private String contributorName;
    private String description;
    private String fileName;
    private String filePath;
    private Long fileSize;
    private Integer downloadCount;
    private Integer semester;
    private String department;
    private String regulation;
    private String uploadedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Constructors
    public StudyResourceResponse() {}
    
    public StudyResourceResponse(Long id, String subjectName, String contributorName, String description,
                                String fileName, String filePath, Long fileSize, Integer downloadCount,
                                Integer semester, String department, String regulation, String uploadedBy,
                                LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.subjectName = subjectName;
        this.contributorName = contributorName;
        this.description = description;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.downloadCount = downloadCount;
        this.semester = semester;
        this.department = department;
        this.regulation = regulation;
        this.uploadedBy = uploadedBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getSubjectName() {
        return subjectName;
    }
    
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
    
    public String getContributorName() {
        return contributorName;
    }
    
    public void setContributorName(String contributorName) {
        this.contributorName = contributorName;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getFileName() {
        return fileName;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    public String getFilePath() {
        return filePath;
    }
    
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    
    public Long getFileSize() {
        return fileSize;
    }
    
    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }
    
    public Integer getDownloadCount() {
        return downloadCount;
    }
    
    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }
    
    public Integer getSemester() {
        return semester;
    }
    
    public void setSemester(Integer semester) {
        this.semester = semester;
    }
    
    public String getDepartment() {
        return department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
    
    public String getRegulation() {
        return regulation;
    }
    
    public void setRegulation(String regulation) {
        this.regulation = regulation;
    }
    
    public String getUploadedBy() {
        return uploadedBy;
    }
    
    public void setUploadedBy(String uploadedBy) {
        this.uploadedBy = uploadedBy;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
