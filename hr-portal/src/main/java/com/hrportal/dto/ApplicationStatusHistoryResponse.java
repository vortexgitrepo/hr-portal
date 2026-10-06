package com.hrportal.dto;

import com.hrportal.enums.ApplicationStatus;

import java.time.LocalDateTime;

public class ApplicationStatusHistoryResponse {

    private Long id;
    private Long applicationId;
    private ApplicationStatus status;
    private String comment;
    private LocalDateTime changedAt;
    private String message;

    public ApplicationStatusHistoryResponse() {
    }

    public ApplicationStatusHistoryResponse(
            Long id,
            Long applicationId,
            ApplicationStatus status,
            String comment,
            LocalDateTime changedAt,
            String message) {

        this.id = id;
        this.applicationId = applicationId;
        this.status = status;
        this.comment = comment;
        this.changedAt = changedAt;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public String getComment() {
        return comment;
    }

    public LocalDateTime getChangedAt() {
        return changedAt;
    }

    public String getMessage() {
        return message;
    }
}
