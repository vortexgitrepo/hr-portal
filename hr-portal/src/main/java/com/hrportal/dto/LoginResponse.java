package com.hrportal.dto;

public class LoginResponse {

    private Long userId;
    private String name;
    private String message;

    public LoginResponse(
            Long userId,
            String name,
            String message) {

        this.userId = userId;
        this.name = name;
        this.message = message;
    }

    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }
}