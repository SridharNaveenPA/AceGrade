package com.acegrade.controller;

import com.acegrade.dto.StudyResourceRequest;
import com.acegrade.dto.StudyResourceResponse;
import com.acegrade.dto.SubjectResponse;
import com.acegrade.entity.StudyResource;
import com.acegrade.entity.Subject;
import com.acegrade.entity.User;
import com.acegrade.repository.StudyResourceRepository;
import com.acegrade.repository.SubjectRepository;
import com.acegrade.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/study-resources")
@CrossOrigin(origins = {"http://localhost:3000", "http://127.0.0.1:5500", "http://localhost:5500", "file://", "*"})
public class StudyResourceController {
    
    @Autowired
    private StudyResourceRepository studyResourceRepository;
    
    @Autowired
    private SubjectRepository subjectRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    private static final String UPLOAD_DIR = "uploads/";
    
    // Get subjects by filters
    @GetMapping("/subjects")
    public ResponseEntity<List<SubjectResponse>> getSubjects(
            @RequestParam(required = false) Integer semester,
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String regulation) {
        
        List<Subject> subjects;
        
        if (semester != null && department != null && regulation != null) {
            subjects = subjectRepository.findBySemesterAndDepartmentAndRegulation(semester, department, regulation);
        } else if (department != null && regulation != null) {
            subjects = subjectRepository.findByDepartmentAndRegulation(department, regulation);
        } else if (regulation != null) {
            subjects = subjectRepository.findByRegulation(regulation);
        } else {
            subjects = subjectRepository.findAll();
        }
        
        List<SubjectResponse> subjectResponses = subjects.stream()
                .map(this::convertToSubjectResponse)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(subjectResponses);
    }
    
    // Get study resources by filters
    @GetMapping
    public ResponseEntity<List<StudyResourceResponse>> getStudyResources(
            @RequestParam(required = false) Integer semester,
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String regulation,
            @RequestParam(required = false) String subjectName,
            @RequestParam(required = false) String searchTerm) {
        
        System.out.println("Searching study resources with filters:");
        System.out.println("Semester: " + semester);
        System.out.println("Department: " + department);
        System.out.println("Regulation: " + regulation);
        System.out.println("Subject Name: " + subjectName);
        System.out.println("Search Term: " + searchTerm);
        
        List<StudyResource> resources;
        
        // Use simpler query if no search term, otherwise use complex query
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            resources = studyResourceRepository.findByBasicFilters(
                    semester, department, regulation, subjectName);
        } else {
            try {
                resources = studyResourceRepository.searchStudyResources(
                        semester, department, regulation, subjectName, searchTerm);
            } catch (Exception e) {
                System.out.println("Complex search failed, falling back to basic search: " + e.getMessage());
                resources = studyResourceRepository.findByBasicFilters(
                        semester, department, regulation, subjectName);
            }
        }
        
        System.out.println("Found " + resources.size() + " study resources");
        
        List<StudyResourceResponse> responses = resources.stream()
                .map(this::convertToStudyResourceResponse)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(responses);
    }
    
    // Get all study resources (for debugging)
    @GetMapping("/all")
    public ResponseEntity<List<StudyResourceResponse>> getAllStudyResources() {
        List<StudyResource> resources = studyResourceRepository.findAll();
        List<StudyResourceResponse> responses = resources.stream()
                .map(this::convertToStudyResourceResponse)
                .collect(Collectors.toList());
        
        System.out.println("All study resources count: " + resources.size());
        return ResponseEntity.ok(responses);
    }
    
    // Upload study resource
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<StudyResourceResponse> uploadStudyResource(
            @RequestParam("file") MultipartFile file,
            @RequestParam("subjectName") String subjectName,
            @RequestParam("contributorName") String contributorName,
            @RequestParam(required = false) String description,
            @RequestParam("semester") Integer semester,
            @RequestParam("department") String department,
            @RequestParam("regulation") String regulation,
            @RequestParam(required = false) Long uploadedBy) {
        
        System.out.println("Uploading study resource:");
        System.out.println("Subject Name: " + subjectName);
        System.out.println("Contributor: " + contributorName);
        System.out.println("Semester: " + semester);
        System.out.println("Department: " + department);
        System.out.println("Regulation: " + regulation);
        System.out.println("File: " + file.getOriginalFilename());
        
        try {
            // Validate file
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            
            if (!file.getContentType().equals("application/pdf")) {
                return ResponseEntity.badRequest().build();
            }
            
            // Find or create subject
            Subject subject = findOrCreateSubject(subjectName, semester, department, regulation);
            
            // Get user if provided
            User user = null;
            if (uploadedBy != null) {
                Optional<User> userOpt = userRepository.findById(uploadedBy);
                user = userOpt.orElse(null);
            }
            
            // Save file
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(UPLOAD_DIR + fileName);
            Files.createDirectories(filePath.getParent());
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            
            // Create study resource
            StudyResource studyResource = new StudyResource();
            studyResource.setSubject(subject);
            studyResource.setContributorName(contributorName);
            studyResource.setDescription(description != null ? description : "Study notes");
            studyResource.setFileName(file.getOriginalFilename());
            studyResource.setFilePath("/uploads/" + fileName);
            studyResource.setFileSize(file.getSize());
            studyResource.setSemester(semester);
            studyResource.setDepartment(department);
            studyResource.setRegulation(regulation);
            studyResource.setUploadedBy(user);
            
            StudyResource savedResource = studyResourceRepository.save(studyResource);
            
            System.out.println("Study resource saved successfully with ID: " + savedResource.getId());
            System.out.println("Subject ID: " + savedResource.getSubject().getId());
            
            return ResponseEntity.ok(convertToStudyResourceResponse(savedResource));
            
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    // Download study resource
    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadStudyResource(@PathVariable Long id) {
        try {
            Optional<StudyResource> resourceOpt = studyResourceRepository.findById(id);
            if (resourceOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            
            StudyResource resource = resourceOpt.get();
            Path filePath = Paths.get("." + resource.getFilePath());
            Resource fileResource = new UrlResource(filePath.toUri());
            
            if (!fileResource.exists()) {
                return ResponseEntity.notFound().build();
            }
            
            // Increment download count
            resource.setDownloadCount(resource.getDownloadCount() + 1);
            studyResourceRepository.save(resource);
            
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFileName() + "\"")
                    .body(fileResource);
                    
        } catch (MalformedURLException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    // Get study resource by ID
    @GetMapping("/{id}")
    public ResponseEntity<StudyResourceResponse> getStudyResource(@PathVariable Long id) {
        Optional<StudyResource> resourceOpt = studyResourceRepository.findById(id);
        if (resourceOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(convertToStudyResourceResponse(resourceOpt.get()));
    }
    
    // Delete study resource
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudyResource(@PathVariable Long id) {
        Optional<StudyResource> resourceOpt = studyResourceRepository.findById(id);
        if (resourceOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        StudyResource resource = resourceOpt.get();
        
        // Delete file from filesystem
        try {
            Path filePath = Paths.get("." + resource.getFilePath());
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            // Log error but continue with database deletion
        }
        
        studyResourceRepository.delete(resource);
        return ResponseEntity.ok().build();
    }
    
    // Helper methods
    private Subject findOrCreateSubject(String subjectName, Integer semester, String department, String regulation) {
        Optional<Subject> existingSubject = subjectRepository.findByNameAndSemesterAndDepartmentAndRegulation(
                subjectName, semester, department, regulation);
        
        if (existingSubject.isPresent()) {
            return existingSubject.get();
        }
        
        // Create new subject
        Subject newSubject = new Subject(subjectName, null, semester, department, regulation);
        return subjectRepository.save(newSubject);
    }
    
    private SubjectResponse convertToSubjectResponse(Subject subject) {
        return new SubjectResponse(
                subject.getId(),
                subject.getName(),
                subject.getCourseCode(),
                subject.getSemester(),
                subject.getDepartment(),
                subject.getRegulation()
        );
    }
    
    private StudyResourceResponse convertToStudyResourceResponse(StudyResource resource) {
        return new StudyResourceResponse(
                resource.getId(),
                resource.getSubject().getName(),
                resource.getContributorName(),
                resource.getDescription(),
                resource.getFileName(),
                resource.getFilePath(),
                resource.getFileSize(),
                resource.getDownloadCount(),
                resource.getSemester(),
                resource.getDepartment(),
                resource.getRegulation(),
                resource.getUploadedBy() != null ? resource.getUploadedBy().getFirstName() + " " + resource.getUploadedBy().getLastName() : null,
                resource.getCreatedAt(),
                resource.getUpdatedAt()
        );
    }
}
