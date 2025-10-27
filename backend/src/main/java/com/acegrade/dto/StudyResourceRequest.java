package com.acegrade.dto;

public class StudyResourceRequest {
    private String subjectName;
    private String contributorName;
    private String description;
    private Integer semester;
    private String department;
    private String regulation;
    
    // Constructors
    public StudyResourceRequest() {}
    
    public StudyResourceRequest(String subjectName, String contributorName, String description, 
                               Integer semester, String department, String regulation) {
        this.subjectName = subjectName;
        this.contributorName = contributorName;
        this.description = description;
        this.semester = semester;
        this.department = department;
        this.regulation = regulation;
    }
    
    // Getters and Setters
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
}
