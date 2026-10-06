package com.hrportal.controller;

import com.hrportal.dto.NotificationRequest;
import com.hrportal.dto.NotificationResponse;
import com.hrportal.entity.Notification;
import com.hrportal.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping
    public ResponseEntity<NotificationResponse> create(@Valid @RequestBody
                                                       NotificationRequest request) {

        Notification notification = notificationService.create(request);

        NotificationResponse response = toResponse(notification, "Notification created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<NotificationResponse>> getAll() {

        List<NotificationResponse> notifications = notificationService.getAll().stream()
                .map(notification -> toResponse(notification, null))
                .toList();

        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationResponse> getById(@PathVariable Long id) {

        Notification notification = notificationService.getById(id);

        NotificationResponse response = toResponse(notification, "Notification fetched successfully");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotificationResponse> update(@PathVariable Long id,
                                                       @Valid @RequestBody
                                                       NotificationRequest request) {

        Notification notification = notificationService.update(id, request);

        NotificationResponse response = toResponse(notification, "Notification updated successfully");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<NotificationResponse> delete(@PathVariable Long id) {

        Notification notification = notificationService.getById(id);
        notificationService.delete(id);

        NotificationResponse response = toResponse(notification, "Notification deleted successfully");
        return ResponseEntity.ok(response);
    }

    private NotificationResponse toResponse(Notification notification, String message) {
        return new NotificationResponse(
                notification.getId(),
                notification.getUser() != null ? notification.getUser().getId() : null,
                notification.getType(),
                notification.getMessage(),
                notification.isRead(),
                notification.getCreatedAt(),
                message
        );
    }
}
