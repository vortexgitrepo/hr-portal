package com.hrportal.controller;

import com.hrportal.dto.ExperienceRequest;
import com.hrportal.dto.ExperienceResponse;
import com.hrportal.entity.Experience;
import com.hrportal.service.ExperienceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/experiences")
public class ExperienceController {

    private final ExperienceService experienceService;

    public ExperienceController(ExperienceService experienceService) {
        this.experienceService = experienceService;
    }

    @PostMapping
    public ResponseEntity<ExperienceResponse> create(@Valid @RequestBody
                                                     ExperienceRequest request) {

        Experience experience = experienceService.create(request);

        ExperienceResponse response = toResponse(experience, "Experience created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ExperienceResponse>> getAll() {

        List<ExperienceResponse> experiences = experienceService.getAll().stream()
                .map(experience -> toResponse(experience, null))
                .toList();

        return ResponseEntity.ok(experiences);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExperienceResponse> getById(@PathVariable Long id) {

        Experience experience = experienceService.getById(id);

        ExperienceResponse response = toResponse(experience, "Experience fetched successfully");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExperienceResponse> update(@PathVariable Long id,
                                                     @Valid @RequestBody
                                                     ExperienceRequest request) {

        Experience experience = experienceService.update(id, request);

        ExperienceResponse response = toResponse(experience, "Experience updated successfully");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ExperienceResponse> delete(@PathVariable Long id) {

        Experience experience = experienceService.getById(id);
        experienceService.delete(id);

        ExperienceResponse response = toResponse(experience, "Experience deleted successfully");
        return ResponseEntity.ok(response);
    }

    private ExperienceResponse toResponse(Experience experience, String message) {
        return new ExperienceResponse(
                experience.getId(),
                experience.getJobSeeker().getId(),
                experience.getCompanyName(),
                experience.getJobTitle(),
                experience.getStartDate(),
                experience.getEndDate(),
                experience.isCurrent(),
                experience.getDescription(),
                message
        );
    }
}
