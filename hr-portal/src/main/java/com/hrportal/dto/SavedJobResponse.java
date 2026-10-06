package com.hrportal.dto;

import java.time.LocalDateTime;

public class SavedJobResponse {

    private Long id;
    private Long jobId;
    private Long userId;
    private LocalDateTime savedAt;
    private String message;

    public SavedJobResponse() {
    }

    public SavedJobResponse(
            Long id,
            Long jobId,
            Long userId,
            LocalDateTime savedAt,
            String message) {

        this.id = id;
        this.jobId = jobId;
        this.userId = userId;
        this.savedAt = savedAt;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public Long getJobId() {
        return jobId;
    }

    public Long getUserId() {
        return userId;
    }

    public LocalDateTime getSavedAt() {
        return savedAt;
    }

    public String getMessage() {
        return message;
    }
}
