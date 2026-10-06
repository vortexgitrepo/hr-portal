package com.hrportal.controller;

import com.hrportal.dto.EducationRequest;
import com.hrportal.dto.EducationResponse;
import com.hrportal.entity.Education;
import com.hrportal.service.EducationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/educations")
public class EducationController {

    private final EducationService educationService;

    public EducationController(EducationService educationService) {
        this.educationService = educationService;
    }

    @PostMapping
    public ResponseEntity<EducationResponse> create(@Valid @RequestBody
                                                    EducationRequest request) {

        Education education = educationService.create(request);

        EducationResponse response = toResponse(education, "Education created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<EducationResponse>> getAll() {

        List<EducationResponse> educations = educationService.getAll().stream()
                .map(education -> toResponse(education, null))
                .toList();

        return ResponseEntity.ok(educations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EducationResponse> getById(@PathVariable Long id) {

        Education education = educationService.getById(id);

        EducationResponse response = toResponse(education, "Education fetched successfully");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EducationResponse> update(@PathVariable Long id,
                                                    @Valid @RequestBody
                                                    EducationRequest request) {

        Education education = educationService.update(id, request);

        EducationResponse response = toResponse(education, "Education updated successfully");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EducationResponse> delete(@PathVariable Long id) {

        Education education = educationService.getById(id);
        educationService.delete(id);

        EducationResponse response = toResponse(education, "Education deleted successfully");
        return ResponseEntity.ok(response);
    }

    private EducationResponse toResponse(Education education, String message) {
        return new EducationResponse(
                education.getId(),
                education.getJobSeeker().getId(),
                education.getDegree(),
                education.getInstitution(),
                education.getFieldOfStudy(),
                education.getStartYear(),
                education.getEndYear(),
                education.getPercentage(),
                message
        );
    }
}
