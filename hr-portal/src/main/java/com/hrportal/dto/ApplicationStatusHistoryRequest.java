package com.hrportal.dto;

import com.hrportal.enums.ApplicationStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ApplicationStatusHistoryRequest {

    @NotNull(message = "Application id is required")
    private Long applicationId;

    private ApplicationStatus status;

    @Size(max = 500, message = "Comment must be at most 500 characters")
    private String comment;

    public ApplicationStatusHistoryRequest() {
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
