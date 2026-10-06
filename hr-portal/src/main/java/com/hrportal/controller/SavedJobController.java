package com.hrportal.controller;

import com.hrportal.dto.SavedJobRequest;
import com.hrportal.dto.SavedJobResponse;
import com.hrportal.entity.SavedJob;
import com.hrportal.service.SavedJobService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/saved-jobs")
public class SavedJobController {

    private final SavedJobService savedJobService;

    public SavedJobController(SavedJobService savedJobService) {
        this.savedJobService = savedJobService;
    }

    @PostMapping
    public ResponseEntity<SavedJobResponse> create(@Valid @RequestBody
                                                   SavedJobRequest request) {

        SavedJob savedJob = savedJobService.create(request);

        SavedJobResponse response = toResponse(savedJob, "SavedJob created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<SavedJobResponse>> getAll() {

        List<SavedJobResponse> savedJobs = savedJobService.getAll().stream()
                .map(savedJob -> toResponse(savedJob, null))
                .toList();

        return ResponseEntity.ok(savedJobs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SavedJobResponse> getById(@PathVariable Long id) {

        SavedJob savedJob = savedJobService.getById(id);

        SavedJobResponse response = toResponse(savedJob, "SavedJob fetched successfully");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SavedJobResponse> update(@PathVariable Long id,
                                                   @Valid @RequestBody
                                                   SavedJobRequest request) {

        SavedJob savedJob = savedJobService.update(id, request);

        SavedJobResponse response = toResponse(savedJob, "SavedJob updated successfully");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SavedJobResponse> delete(@PathVariable Long id) {

        SavedJob savedJob = savedJobService.getById(id);
        savedJobService.delete(id);

        SavedJobResponse response = toResponse(savedJob, "SavedJob deleted successfully");
        return ResponseEntity.ok(response);
    }

    private SavedJobResponse toResponse(SavedJob savedJob, String message) {
        return new SavedJobResponse(
                savedJob.getId(),
                savedJob.getJob().getId(),
                savedJob.getUser().getId(),
                savedJob.getSavedAt(),
                message
        );
    }
}
