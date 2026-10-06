package com.hrportal.controller;

import com.hrportal.dto.ApplicationRequest;
import com.hrportal.dto.ApplicationResponse;
import com.hrportal.entity.Application;
import com.hrportal.service.ApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping
    public ResponseEntity<ApplicationResponse> create(@Valid @RequestBody
                                                      ApplicationRequest request) {

        Application application = applicationService.create(request);

        ApplicationResponse response = toResponse(application, "Application created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ApplicationResponse>> getAll() {

        List<ApplicationResponse> applications = applicationService.getAll().stream()
                .map(application -> toResponse(application, null))
                .toList();

        return ResponseEntity.ok(applications);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationResponse> getById(@PathVariable Long id) {

        Application application = applicationService.getById(id);

        ApplicationResponse response = toResponse(application, "Application fetched successfully");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApplicationResponse> update(@PathVariable Long id,
                                                      @Valid @RequestBody
                                                      ApplicationRequest request) {

        Application application = applicationService.update(id, request);

        ApplicationResponse response = toResponse(application, "Application updated successfully");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApplicationResponse> delete(@PathVariable Long id) {

        Application application = applicationService.getById(id);
        applicationService.delete(id);

        ApplicationResponse response = toResponse(application, "Application deleted successfully");
        return ResponseEntity.ok(response);
    }

    private ApplicationResponse toResponse(Application application, String message) {
        return new ApplicationResponse(
                application.getId(),
                application.getJob().getId(),
                application.getJobSeeker().getId(),
                application.getResume() != null ? application.getResume().getId() : null,
                application.getStatus(),
                application.getAppliedAt(),
                application.getUpdatedAt(),
                message
        );
    }
}
