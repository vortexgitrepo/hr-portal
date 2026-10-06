package com.hrportal.dto;

import com.hrportal.enums.InterviewStatus;
import com.hrportal.enums.InterviewType;

import java.time.LocalDateTime;

public class InterviewResponse {

    private Long id;
    private Long applicationId;
    private LocalDateTime interviewDate;
    private InterviewType interviewType;
    private String meetingLink;
    private InterviewStatus status;
    private String feedback;
    private LocalDateTime createdAt;
    private String message;

    public InterviewResponse() {
    }

    public InterviewResponse(
            Long id,
            Long applicationId,
            LocalDateTime interviewDate,
            InterviewType interviewType,
            String meetingLink,
            InterviewStatus status,
            String feedback,
            LocalDateTime createdAt,
            String message) {

        this.id = id;
        this.applicationId = applicationId;
        this.interviewDate = interviewDate;
        this.interviewType = interviewType;
        this.meetingLink = meetingLink;
        this.status = status;
        this.feedback = feedback;
        this.createdAt = createdAt;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public LocalDateTime getInterviewDate() {
        return interviewDate;
    }

    public InterviewType getInterviewType() {
        return interviewType;
    }

    public String getMeetingLink() {
        return meetingLink;
    }

    public InterviewStatus getStatus() {
        return status;
    }

    public String getFeedback() {
        return feedback;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getMessage() {
        return message;
    }
}
