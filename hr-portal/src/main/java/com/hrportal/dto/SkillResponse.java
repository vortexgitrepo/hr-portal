package com.hrportal.dto;

public class SkillResponse {

    private Long id;
    private String name;
    private String message;

    public SkillResponse() {
    }

    public SkillResponse(Long id, String name, String message) {
        this.id = id;
        this.name = name;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }
}
