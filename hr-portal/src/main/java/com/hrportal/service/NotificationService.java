package com.hrportal.service;

import com.hrportal.dto.NotificationRequest;
import com.hrportal.entity.Notification;
import com.hrportal.entity.User;
import com.hrportal.exception.NotificationNotFoundException;
import com.hrportal.exception.UserNotFoundException;
import com.hrportal.repository.NotificationRepository;
import com.hrportal.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationService(NotificationRepository notificationRepository,
                               UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    public Notification create(NotificationRequest request) {

        Notification notification = new Notification();
        applyRequest(notification, request);
        return notificationRepository.save(notification);
    }

    public List<Notification> getAll() {
        return notificationRepository.findAll();
    }

    public Notification getById(Long id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new NotificationNotFoundException(
                        "Notification not found with id: " + id
                ));
    }

    public Notification update(Long id, NotificationRequest request) {

        Notification notification = getById(id);

        applyRequest(notification, request);
        return notificationRepository.save(notification);
    }

    public void delete(Long id) {

        Notification notification = getById(id);
        notificationRepository.delete(notification);
    }

    private void applyRequest(Notification notification, NotificationRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException(
                        "User not found with id: " + request.getUserId()
                ));

        notification.setUser(user);
        notification.setType(request.getType());
        notification.setMessage(request.getMessage());
        notification.setRead(request.isRead());
    }
}
