package com.hrportal.dto;

public class JobSeekerProfileResponse {

    private Long id;
    private Long userId;
    private String headline;
    private String summary;
    private Integer experienceYears;
    private String currentCompany;
    private String currentLocation;
    private String preferredLocation;
    private Double expectedSalary;
    private Integer noticePeriod;
    private String profileImageUrl;
    private String message;

    public JobSeekerProfileResponse(
            Long id,
            Long userId,
            String headline,
            String summary,
            Integer experienceYears,
            String currentCompany,
            String currentLocation,
            String preferredLocation,
            Double expectedSalary,
            Integer noticePeriod,
            String profileImageUrl,
            String message) {

        this.id = id;
        this.userId = userId;
        this.headline = headline;
        this.summary = summary;
        this.experienceYears = experienceYears;
        this.currentCompany = currentCompany;
        this.currentLocation = currentLocation;
        this.preferredLocation = preferredLocation;
        this.expectedSalary = expectedSalary;
        this.noticePeriod = noticePeriod;
        this.profileImageUrl = profileImageUrl;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getHeadline() {
        return headline;
    }

    public String getSummary() {
        return summary;
    }

    public Integer getExperienceYears() {
        return experienceYears;
    }

    public String getCurrentCompany() {
        return currentCompany;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public String getPreferredLocation() {
        return preferredLocation;
    }

    public Double getExpectedSalary() {
        return expectedSalary;
    }

    public Integer getNoticePeriod() {
        return noticePeriod;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getMessage() {
        return message;
    }
}
