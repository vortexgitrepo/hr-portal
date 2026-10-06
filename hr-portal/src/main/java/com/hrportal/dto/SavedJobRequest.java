package com.hrportal.dto;

import jakarta.validation.constraints.NotNull;

public class SavedJobRequest {

    @NotNull(message = "Job id is required")
    private Long jobId;

    @NotNull(message = "User id is required")
    private Long userId;

    public SavedJobRequest() {
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
