package com.hrportal.dto;

import java.time.LocalDateTime;

public class ResumeResponse {

    private Long id;
    private Long jobSeekerId;
    private String fileName;
    private String resumeUrl;
    private boolean isDefault;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String message;

    public ResumeResponse(
            Long id,
            Long jobSeekerId,
            String fileName,
            String resumeUrl,
            boolean isDefault,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            String message) {

        this.id = id;
        this.jobSeekerId = jobSeekerId;
        this.fileName = fileName;
        this.resumeUrl = resumeUrl;
        this.isDefault = isDefault;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public Long getJobSeekerId() {
        return jobSeekerId;
    }

    public String getFileName() {
        return fileName;
    }

    public String getResumeUrl() {
        return resumeUrl;
    }

    public boolean getIsDefault() {
        return isDefault;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public String getMessage() {
        return message;
    }
}
