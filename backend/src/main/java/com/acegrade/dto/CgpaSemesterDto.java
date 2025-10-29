package com.acegrade.dto;

import java.util.ArrayList;
import java.util.List;

public class CgpaSemesterDto {
    private Integer semesterNumber;
    private List<CgpaCourseDto> courses = new ArrayList<>();

    public CgpaSemesterDto() {}

    public CgpaSemesterDto(Integer semesterNumber, List<CgpaCourseDto> courses) {
        this.semesterNumber = semesterNumber;
        this.courses = courses;
    }

    public Integer getSemesterNumber() { return semesterNumber; }
    public void setSemesterNumber(Integer semesterNumber) { this.semesterNumber = semesterNumber; }

    public List<CgpaCourseDto> getCourses() { return courses; }
    public void setCourses(List<CgpaCourseDto> courses) { this.courses = courses; }
}


