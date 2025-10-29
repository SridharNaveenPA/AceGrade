package com.acegrade.dto;

public class CgpaCourseDto {
    private String courseName;
    private String grade;
    private Double credits;

    public CgpaCourseDto() {}

    public CgpaCourseDto(String courseName, String grade, Double credits) {
        this.courseName = courseName;
        this.grade = grade;
        this.credits = credits;
    }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }

    public Double getCredits() { return credits; }
    public void setCredits(Double credits) { this.credits = credits; }
}


