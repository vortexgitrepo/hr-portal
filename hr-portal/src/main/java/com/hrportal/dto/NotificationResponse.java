package com.hrportal.dto;

import com.hrportal.enums.NotificationType;

import java.time.LocalDateTime;

public class NotificationResponse {

    private Long id;
    private Long userId;
    private NotificationType type;
    private String notificationMessage;
    private boolean read;
    private LocalDateTime createdAt;
    private String message;

    public NotificationResponse() {
    }

    public NotificationResponse(
            Long id,
            Long userId,
            NotificationType type,
            String notificationMessage,
            boolean read,
            LocalDateTime createdAt,
            String message) {

        this.id = id;
        this.userId = userId;
        this.type = type;
        this.notificationMessage = notificationMessage;
        this.read = read;
        this.createdAt = createdAt;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public NotificationType getType() {
        return type;
    }

    public String getNotificationMessage() {
        return notificationMessage;
    }

    public boolean isRead() {
        return read;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public String getMessage() {
        return message;
    }
}
