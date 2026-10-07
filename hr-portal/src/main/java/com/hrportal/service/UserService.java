package com.hrportal.service;

import com.hrportal.dto.LoginRequest;
import com.hrportal.dto.LoginResponse;
import com.hrportal.dto.UserRegistrationRequest;
import com.hrportal.entity.User;
import com.hrportal.enums.Role;
import com.hrportal.exception.EmailAlreadyExistsException;
import com.hrportal.exception.InvalidCredentialsException;
import com.hrportal.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(UserRegistrationRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already registered");
        }
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Role role = Role.CANDIDATE;
        if (request.getRole() != null && !request.getRole().isBlank()) {
            try {
                Role requestedRole = Role.valueOf(request.getRole().toUpperCase());
                if (requestedRole == Role.HR) {
                    role = Role.HR;
                }
            } catch (IllegalArgumentException ignored) {
            }
        }
        user.setRole(role);

        return userRepository.save(user);
    }

    public User login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException(
                        "Invalid email or password"
                ));
        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }
        return user;
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
