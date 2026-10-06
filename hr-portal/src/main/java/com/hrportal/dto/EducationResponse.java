package com.hrportal.dto;

public class EducationResponse {

    private Long id;
    private Long jobSeekerId;
    private String degree;
    private String institution;
    private String fieldOfStudy;
    private Integer startYear;
    private Integer endYear;
    private Double percentage;
    private String message;

    public EducationResponse(
            Long id,
            Long jobSeekerId,
            String degree,
            String institution,
            String fieldOfStudy,
            Integer startYear,
            Integer endYear,
            Double percentage,
            String message) {

        this.id = id;
        this.jobSeekerId = jobSeekerId;
        this.degree = degree;
        this.institution = institution;
        this.fieldOfStudy = fieldOfStudy;
        this.startYear = startYear;
        this.endYear = endYear;
        this.percentage = percentage;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public Long getJobSeekerId() {
        return jobSeekerId;
    }

    public String getDegree() {
        return degree;
    }

    public String getInstitution() {
        return institution;
    }

    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    public Integer getStartYear() {
        return startYear;
    }

    public Integer getEndYear() {
        return endYear;
    }

    public Double getPercentage() {
        return percentage;
    }

    public String getMessage() {
        return message;
    }
}
