package com.hrportal.service;

import com.hrportal.dto.UserRegistrationRequest;
import com.hrportal.entity.User;
import com.hrportal.exception.EmailAlreadyExistsException;
import com.hrportal.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository= userRepository;
    }

    public User register(UserRegistrationRequest request) {

        if(userRepository.existsByEmail(request.getEmail())){
            throw new EmailAlreadyExistsException("Email already registere");
        }
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        return userRepository.save(user);
    }
}
