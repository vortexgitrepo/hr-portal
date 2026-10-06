package com.hrportal.controller;

import com.hrportal.dto.ResumeRequest;
import com.hrportal.dto.ResumeResponse;
import com.hrportal.entity.Resume;
import com.hrportal.service.ResumeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resumes")
public class ResumeController {

    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @PostMapping
    public ResponseEntity<ResumeResponse> create(@Valid @RequestBody
                                                 ResumeRequest request) {

        Resume resume = resumeService.create(request);

        ResumeResponse response = toResponse(resume, "Resume created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ResumeResponse>> getAll() {

        List<ResumeResponse> resumes = resumeService.getAll().stream()
                .map(resume -> toResponse(resume, null))
                .toList();

        return ResponseEntity.ok(resumes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResumeResponse> getById(@PathVariable Long id) {

        Resume resume = resumeService.getById(id);

        ResumeResponse response = toResponse(resume, "Resume fetched successfully");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResumeResponse> update(@PathVariable Long id,
                                                 @Valid @RequestBody
                                                 ResumeRequest request) {

        Resume resume = resumeService.update(id, request);

        ResumeResponse response = toResponse(resume, "Resume updated successfully");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResumeResponse> delete(@PathVariable Long id) {

        Resume resume = resumeService.getById(id);
        resumeService.delete(id);

        ResumeResponse response = toResponse(resume, "Resume deleted successfully");
        return ResponseEntity.ok(response);
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
