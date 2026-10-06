package com.hrportal.controller;

import com.hrportal.dto.ApplicationStatusHistoryRequest;
import com.hrportal.dto.ApplicationStatusHistoryResponse;
import com.hrportal.entity.ApplicationStatusHistory;
import com.hrportal.service.ApplicationStatusHistoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/application-status-histories")
public class ApplicationStatusHistoryController {

    private final ApplicationStatusHistoryService applicationStatusHistoryService;

    public ApplicationStatusHistoryController(
            ApplicationStatusHistoryService applicationStatusHistoryService) {
        this.applicationStatusHistoryService = applicationStatusHistoryService;
    }

    @PostMapping
    public ResponseEntity<ApplicationStatusHistoryResponse> create(@Valid @RequestBody
                                                                   ApplicationStatusHistoryRequest request) {

        ApplicationStatusHistory applicationStatusHistory =
                applicationStatusHistoryService.create(request);

        ApplicationStatusHistoryResponse response =
                toResponse(applicationStatusHistory, "ApplicationStatusHistory created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ApplicationStatusHistoryResponse>> getAll() {

        List<ApplicationStatusHistoryResponse> applicationStatusHistories =
                applicationStatusHistoryService.getAll().stream()
                        .map(applicationStatusHistory -> toResponse(applicationStatusHistory, null))
                        .toList();

        return ResponseEntity.ok(applicationStatusHistories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationStatusHistoryResponse> getById(@PathVariable Long id) {

        ApplicationStatusHistory applicationStatusHistory =
                applicationStatusHistoryService.getById(id);

        ApplicationStatusHistoryResponse response =
                toResponse(applicationStatusHistory, "ApplicationStatusHistory fetched successfully");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApplicationStatusHistoryResponse> update(@PathVariable Long id,
                                                                   @Valid @RequestBody
                                                                   ApplicationStatusHistoryRequest request) {

        ApplicationStatusHistory applicationStatusHistory =
                applicationStatusHistoryService.update(id, request);

        ApplicationStatusHistoryResponse response =
                toResponse(applicationStatusHistory, "ApplicationStatusHistory updated successfully");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApplicationStatusHistoryResponse> delete(@PathVariable Long id) {

        ApplicationStatusHistory applicationStatusHistory =
                applicationStatusHistoryService.getById(id);
        applicationStatusHistoryService.delete(id);

        ApplicationStatusHistoryResponse response =
                toResponse(applicationStatusHistory, "ApplicationStatusHistory deleted successfully");
        return ResponseEntity.ok(response);
    }

    private ApplicationStatusHistoryResponse toResponse(
            ApplicationStatusHistory applicationStatusHistory, String message) {

        return new ApplicationStatusHistoryResponse(
                applicationStatusHistory.getId(),
                applicationStatusHistory.getApplication().getId(),
                applicationStatusHistory.getStatus(),
                applicationStatusHistory.getComment(),
                applicationStatusHistory.getChangedAt(),
                message
        );
    }
}
