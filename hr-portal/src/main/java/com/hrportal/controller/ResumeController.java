package com.hrportal.controller;

import com.hrportal.dto.ResumeRequest;
import com.hrportal.dto.ResumeResponse;
import com.hrportal.entity.Resume;
import com.hrportal.service.ResumeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resumes")
public class ResumeController {

    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @Transactional(readOnly = true)
    @GetMapping("/my")
    public ResponseEntity<List<ResumeResponse>> getMyResumes() {
        Long userId = getCurrentUserId();
        List<ResumeResponse> resumes = resumeService.getByJobSeekerId(userId).stream()
                .map(resume -> toResponse(resume, null))
                .toList();
        return ResponseEntity.ok(resumes);
    }

    @Transactional
    @PostMapping("/my")
    public ResponseEntity<ResumeResponse> createMyResume(@Valid @RequestBody ResumeRequest request) {
        Long userId = getCurrentUserId();
        request.setJobSeekerId(userId);
        Resume resume = resumeService.create(request);
        ResumeResponse response = toResponse(resume, "Resume created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Transactional
    @PutMapping("/my/{id}")
    public ResponseEntity<ResumeResponse> updateMyResume(@PathVariable Long id,
                                                         @Valid @RequestBody ResumeRequest request) {
        Long userId = getCurrentUserId();
        Resume resume = resumeService.update(id, request, userId);
        ResumeResponse response = toResponse(resume, "Resume updated successfully");
        return ResponseEntity.ok(response);
    }

    @Transactional
    @DeleteMapping("/my/{id}")
    public ResponseEntity<ResumeResponse> deleteMyResume(@PathVariable Long id) {
        Long userId = getCurrentUserId();
        Resume resume = resumeService.getById(id);
        resumeService.delete(id, userId);
        ResumeResponse response = toResponse(resume, "Resume deleted successfully");
        return ResponseEntity.ok(response);
    }

    private Long getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }
        return (Long) auth.getPrincipal();
    }

    private ResumeResponse toResponse(Resume resume, String message) {
        return new ResumeResponse(
                resume.getId(),
                resume.getJobSeeker().getId(),
                resume.getFileName(),
                resume.getResumeUrl(),
                resume.isDefault(),
                resume.getCreatedAt(),
                resume.getUpdatedAt(),
                message
        );
    }
}
