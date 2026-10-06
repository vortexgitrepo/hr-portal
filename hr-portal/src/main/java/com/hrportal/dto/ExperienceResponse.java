package com.hrportal.dto;

import java.time.LocalDate;

public class ExperienceResponse {

    private Long id;
    private Long jobSeekerId;
    private String companyName;
    private String jobTitle;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean current;
    private String description;
    private String message;

    public ExperienceResponse(
            Long id,
            Long jobSeekerId,
            String companyName,
            String jobTitle,
            LocalDate startDate,
            LocalDate endDate,
            boolean current,
            String description,
            String message) {

        this.id = id;
        this.jobSeekerId = jobSeekerId;
        this.companyName = companyName;
        this.jobTitle = jobTitle;
        this.startDate = startDate;
        this.endDate = endDate;
        this.current = current;
        this.description = description;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public Long getJobSeekerId() {
        return jobSeekerId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public boolean isCurrent() {
        return current;
    }

    public String getDescription() {
        return description;
    }

    public String getMessage() {
        return message;
    }
}
