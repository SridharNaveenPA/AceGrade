package com.acegrade.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "subjects")
public class Subject {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    
    @Column(name = "course_code", length = 20)
    private String courseCode;
    
    @Column(name = "semester", nullable = false)
    private Integer semester;
    
    @Column(name = "department", nullable = false, length = 50)
    private String department;
    
    @Column(name = "regulation", nullable = false, length = 10)
    private String regulation;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<StudyResource> studyResources;
    
    // Constructors
    public Subject() {
        this.createdAt = LocalDateTime.now();
    }
    
    public Subject(String name, String courseCode, Integer semester, String department, String regulation) {
        this();
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
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public List<StudyResource> getStudyResources() {
        return studyResources;
    }
    
    public void setStudyResources(List<StudyResource> studyResources) {
        this.studyResources = studyResources;
    }
    
    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", courseCode='" + courseCode + '\'' +
                ", semester=" + semester +
                ", department='" + department + '\'' +
                ", regulation='" + regulation + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
