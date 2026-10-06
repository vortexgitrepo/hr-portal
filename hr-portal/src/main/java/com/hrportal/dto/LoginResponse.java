package com.hrportal.dto;

public class LoginResponse {

    private Long userId;
    private String name;
    private String message;
    private String token;
    private String tokenType;

    public LoginResponse(
            Long userId,
            String name,
            String message,
            String token,
            String tokenType) {

        this.userId = userId;
        this.name = name;
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