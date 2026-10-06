package com.hrportal.dto;

import com.hrportal.enums.EmploymentType;
import com.hrportal.enums.JobStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class JobResponse {

    private Long id;
    private String title;
    private String description;
    private Long companyId;
    private String location;
    private EmploymentType employmentType;
    private Integer experienceMin;
    private Integer experienceMax;
    private BigDecimal salaryMin;
    private BigDecimal salaryMax;
    private JobStatus status;
    private List<Long> skillIds;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String message;

    public JobResponse() {
    }

    public JobResponse(
            Long id,
            String title,
            String description,
            Long companyId,
            String location,
            EmploymentType employmentType,
            Integer experienceMin,
            Integer experienceMax,
            BigDecimal salaryMin,
            BigDecimal salaryMax,
            JobStatus status,
            List<Long> skillIds,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            String message) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.companyId = companyId;
        this.location = location;
        this.employmentType = employmentType;
        this.experienceMin = experienceMin;
        this.experienceMax = experienceMax;
        this.salaryMin = salaryMin;
        this.salaryMax = salaryMax;
        this.status = status;
        this.skillIds = skillIds;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public String getLocation() {
        return location;
    }

    public EmploymentType getEmploymentType() {
        return employmentType;
    }

    public Integer getExperienceMin() {
        return experienceMin;
    }

    public Integer getExperienceMax() {
        return experienceMax;
    }

    public BigDecimal getSalaryMin() {
        return salaryMin;
    }

    public BigDecimal getSalaryMax() {
        return salaryMax;
    }

    public JobStatus getStatus() {
        return status;
    }

    public List<Long> getSkillIds() {
        return skillIds;
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
