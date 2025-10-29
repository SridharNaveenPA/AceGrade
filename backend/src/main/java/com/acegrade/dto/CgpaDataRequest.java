package com.acegrade.dto;

import java.util.ArrayList;
import java.util.List;

public class CgpaDataRequest {
    private List<CgpaSemesterDto> semesters = new ArrayList<>();

    public CgpaDataRequest() {}

    public CgpaDataRequest(List<CgpaSemesterDto> semesters) {
        this.semesters = semesters;
    }

    public List<CgpaSemesterDto> getSemesters() { return semesters; }
    public void setSemesters(List<CgpaSemesterDto> semesters) { this.semesters = semesters; }
}


