package com.acegrade.controller;

import com.acegrade.dto.CgpaCourseDto;
import com.acegrade.dto.CgpaDataRequest;
import com.acegrade.dto.CgpaSemesterDto;
import com.acegrade.entity.CgpaCourse;
import com.acegrade.entity.CgpaSemester;
import com.acegrade.entity.User;
import com.acegrade.repository.CgpaSemesterRepository;
import com.acegrade.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cgpa")
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:5500", "http://localhost:5500", "file://", "*"})
public class CgpaController {

    @Autowired
    private CgpaSemesterRepository cgpaSemesterRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{userId}")
    public ResponseEntity<List<CgpaSemesterDto>> getCgpaData(@PathVariable Long userId) {
        List<CgpaSemester> semesters = cgpaSemesterRepository.findByUserIdOrderBySemesterNumberAsc(userId);
        List<CgpaSemesterDto> response = semesters.stream().map(this::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Void> saveCgpaData(@PathVariable Long userId, @RequestBody CgpaDataRequest request) {
        Optional<User> userOpt = userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        // Replace all existing data for this user
        cgpaSemesterRepository.deleteByUserId(userId);

        for (CgpaSemesterDto semDto : request.getSemesters()) {
            CgpaSemester sem = new CgpaSemester();
            sem.setUser(userOpt.get());
            sem.setSemesterNumber(semDto.getSemesterNumber());

            for (CgpaCourseDto cDto : semDto.getCourses()) {
                CgpaCourse c = new CgpaCourse();
                c.setSemester(sem);
                c.setCourseName(cDto.getCourseName());
                c.setGrade(cDto.getGrade());
                c.setCredits(cDto.getCredits());
                sem.getCourses().add(c);
            }
            cgpaSemesterRepository.save(sem);
        }

        return ResponseEntity.ok().build();
    }

    private CgpaSemesterDto toDto(CgpaSemester sem) {
        CgpaSemesterDto dto = new CgpaSemesterDto();
        dto.setSemesterNumber(sem.getSemesterNumber());
        dto.setCourses(sem.getCourses().stream().map(c -> new CgpaCourseDto(
                c.getCourseName(), c.getGrade(), c.getCredits()
        )).collect(Collectors.toList()));
        return dto;
    }
}


