package com.hrportal.dto;

import java.time.LocalDateTime;

public class CompanyResponse {

    private Long id;
    private String name;
    private String description;
    private String website;
    private String industry;
    private String location;
    private String companySize;
    private String logoUrl;
    private Long ownerId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String message;

    public CompanyResponse() {
    }

    public CompanyResponse(
            Long id,
            String name,
            String description,
            String website,
            String industry,
            String location,
            String companySize,
            String logoUrl,
            Long ownerId,
            LocalDateTime createdAt,
            LocalDateTime updatedAt,
            String message) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.website = website;
        this.industry = industry;
        this.location = location;
        this.companySize = companySize;
        this.logoUrl = logoUrl;
        this.ownerId = ownerId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getWebsite() {
        return website;
    }

    public String getIndustry() {
        return industry;
    }

    public String getLocation() {
        return location;
    }

    public String getCompanySize() {
        return companySize;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public Long getOwnerId() {
        return ownerId;
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
