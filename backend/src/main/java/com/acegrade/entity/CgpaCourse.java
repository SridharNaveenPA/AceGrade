package com.acegrade.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cgpa_courses")
public class CgpaCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "semester_id", nullable = false)
    private CgpaSemester semester;

    @Column(name = "course_name", length = 200)
    private String courseName;

    @Column(name = "grade", length = 10)
    private String grade;

    @Column(name = "credits")
    private Double credits;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public CgpaSemester getSemester() { return semester; }
    public void setSemester(CgpaSemester semester) { this.semester = semester; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }

    public Double getCredits() { return credits; }
    public void setCredits(Double credits) { this.credits = credits; }
}


