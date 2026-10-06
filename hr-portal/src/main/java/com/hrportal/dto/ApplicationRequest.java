package com.hrportal.dto;

import com.hrportal.enums.ApplicationStatus;
import jakarta.validation.constraints.NotNull;

public class ApplicationRequest {

    @NotNull(message = "Job id is required")
    private Long jobId;

    @NotNull(message = "Job seeker id is required")
    private Long jobSeekerId;

    private Long resumeId;

    private ApplicationStatus status;

    public ApplicationRequest() {
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public Long getJobSeekerId() {
        return jobSeekerId;
    }

    public void setJobSeekerId(Long jobSeekerId) {
        this.jobSeekerId = jobSeekerId;
    }

    public Long getResumeId() {
        return resumeId;
    }

    public void setResumeId(Long resumeId) {
        this.resumeId = resumeId;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }
}
