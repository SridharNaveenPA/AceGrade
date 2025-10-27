package com.acegrade.dto;

public class SubjectResponse {
    private Long id;
    private String name;
    private String courseCode;
    private Integer semester;
    private String department;
    private String regulation;
    
    // Constructors
    public SubjectResponse() {}
    
    public SubjectResponse(Long id, String name, String courseCode, Integer semester, String department, String regulation) {
        this.id = id;
        this.name = name;
        this.courseCode = courseCode;
        this.semester = semester;
        this.department = department;
        this.regulation = regulation;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getCourseCode() {
        return courseCode;
    }
    
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
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
