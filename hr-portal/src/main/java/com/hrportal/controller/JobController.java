package com.hrportal.controller;

import com.hrportal.dto.JobRequest;
import com.hrportal.dto.JobResponse;
import com.hrportal.entity.Job;
import com.hrportal.service.JobService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping
    public ResponseEntity<JobResponse> create(@Valid @RequestBody
                                              JobRequest request) {

        Job job = jobService.create(request);

        JobResponse response = toResponse(job, "Job created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<JobResponse>> getAll() {

        List<JobResponse> jobs = jobService.getAll().stream()
                .map(job -> toResponse(job, null))
                .toList();

        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobResponse> getById(@PathVariable Long id) {

        Job job = jobService.getById(id);

        JobResponse response = toResponse(job, "Job fetched successfully");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobResponse> update(@PathVariable Long id,
                                              @Valid @RequestBody
                                              JobRequest request) {

        Job job = jobService.update(id, request);

        JobResponse response = toResponse(job, "Job updated successfully");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<JobResponse> delete(@PathVariable Long id) {

        Job job = jobService.getById(id);
        jobService.delete(id);

        JobResponse response = toResponse(job, "Job deleted successfully");
        return ResponseEntity.ok(response);
    }

    private JobResponse toResponse(Job job, String message) {
        return new JobResponse(
                job.getId(),
                job.getTitle(),
                job.getDescription(),
                job.getCompany() != null ? job.getCompany().getId() : null,
                job.getLocation(),
                job.getEmploymentType(),
                job.getExperienceMin(),
                job.getExperienceMax(),
                job.getSalaryMin(),
                job.getSalaryMax(),
                job.getStatus(),
                job.getSkills().stream()
                        .map(skill -> skill.getId())
                        .toList(),
                job.getCreatedAt(),
                job.getUpdatedAt(),
                message
        );
    }
}
