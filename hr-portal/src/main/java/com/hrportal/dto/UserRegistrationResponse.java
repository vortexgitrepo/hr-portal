package com.hrportal.dto;

public class UserRegistrationResponse {

    private Long userId;
    private String name;
    private String email;
    private String role;
    private String message;

    public UserRegistrationResponse() {
    }

    public UserRegistrationResponse(
            Long userId,
            String name,
            String email,
            String role,
            String message) {

        this.userId = userId;
        this.name = name;
        this.email = email;
        this.role = role;
        this.message = message;
    }

    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public String getMessage() {
        return message;
    }
}
