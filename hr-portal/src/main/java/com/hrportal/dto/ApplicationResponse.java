package com.hrportal.dto;

import com.hrportal.enums.ApplicationStatus;

import java.time.LocalDateTime;

public class ApplicationResponse {

    private Long id;
    private Long jobId;
    private Long jobSeekerId;
    private Long resumeId;
    private ApplicationStatus status;
    private LocalDateTime appliedAt;
    private LocalDateTime updatedAt;
    private String message;

    public ApplicationResponse() {
    }

    public ApplicationResponse(
            Long id,
            Long jobId,
            Long jobSeekerId,
            Long resumeId,
            ApplicationStatus status,
            LocalDateTime appliedAt,
            LocalDateTime updatedAt,
            String message) {

        this.id = id;
        this.jobId = jobId;
        this.jobSeekerId = jobSeekerId;
        this.resumeId = resumeId;
        this.status = status;
        this.appliedAt = appliedAt;
        this.updatedAt = updatedAt;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public Long getJobId() {
        return jobId;
    }

    public Long getJobSeekerId() {
        return jobSeekerId;
    }

    public Long getResumeId() {
        return resumeId;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public LocalDateTime getAppliedAt() {
        return appliedAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public String getMessage() {
        return message;
    }
}
