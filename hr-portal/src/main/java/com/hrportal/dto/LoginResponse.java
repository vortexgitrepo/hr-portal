package com.hrportal.dto;

public class LoginResponse {

    private Long userId;
    private String name;
    private String email;
    private String role;
    private String message;
    private String token;
    private String tokenType;

    public LoginResponse(
            Long userId,
            String name,
            String email,
            String role,
            String message,
            String token,
            String tokenType) {

        this.userId = userId;
        this.name = name;
        this.email = email;
        this.role = role;
        this.message = message;
        this.token = token;
        this.tokenType = tokenType;
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

    public String getToken() {
        return token;
    }

    public String getTokenType() {
        return tokenType;
    }
}
