package com.hrportal.controller;

import com.hrportal.dto.InterviewRequest;
import com.hrportal.dto.InterviewResponse;
import com.hrportal.entity.Interview;
import com.hrportal.service.InterviewService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interviews")
public class InterviewController {

    private final InterviewService interviewService;

    public InterviewController(InterviewService interviewService) {
        this.interviewService = interviewService;
    }

    @PostMapping
    public ResponseEntity<InterviewResponse> create(@Valid @RequestBody
                                                    InterviewRequest request) {

        Interview interview = interviewService.create(request);

        InterviewResponse response = toResponse(interview, "Interview created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<InterviewResponse>> getAll() {

        List<InterviewResponse> interviews = interviewService.getAll().stream()
                .map(interview -> toResponse(interview, null))
                .toList();

        return ResponseEntity.ok(interviews);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InterviewResponse> getById(@PathVariable Long id) {

        Interview interview = interviewService.getById(id);

        InterviewResponse response = toResponse(interview, "Interview fetched successfully");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InterviewResponse> update(@PathVariable Long id,
                                                    @Valid @RequestBody
                                                    InterviewRequest request) {

        Interview interview = interviewService.update(id, request);

        InterviewResponse response = toResponse(interview, "Interview updated successfully");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<InterviewResponse> delete(@PathVariable Long id) {

        Interview interview = interviewService.getById(id);
        interviewService.delete(id);

        InterviewResponse response = toResponse(interview, "Interview deleted successfully");
        return ResponseEntity.ok(response);
    }

    private InterviewResponse toResponse(Interview interview, String message) {
        return new InterviewResponse(
                interview.getId(),
                interview.getApplication().getId(),
                interview.getInterviewDate(),
                interview.getInterviewType(),
                interview.getMeetingLink(),
                interview.getStatus(),
                interview.getFeedback(),
                interview.getCreatedAt(),
                message
        );
    }
}
