package com.hrportal.controller;

import com.hrportal.dto.LoginRequest;
import com.hrportal.dto.LoginResponse;
import com.hrportal.dto.UserRegistrationRequest;
import com.hrportal.dto.UserRegistrationResponse;
import com.hrportal.entity.User;
import com.hrportal.service.JwtService;
import com.hrportal.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

private final UserService userService;

private final JwtService jwtService;


public UserController(UserService userService, JwtService jwtService){
    this.userService=userService;
    this.jwtService=jwtService;
}

@PostMapping("/register")
public ResponseEntity<UserRegistrationResponse> register(@Valid @RequestBody
                                           UserRegistrationRequest request){

   User user = userService.register(request);

    UserRegistrationResponse response = new UserRegistrationResponse(user.getId(),user.getName(),
            user.getEmail(),"User registered successfully");
    return ResponseEntity.status(HttpStatus.CREATED)
            .body(response);
}

@PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody
                                               LoginRequest loginRequest){
    User user = userService.login(loginRequest);
    String token = jwtService.generateToken(user);
    LoginResponse response = new LoginResponse(user.getId(),
            user.getName(),"Login Succesfully", token, "Bearer");
    return ResponseEntity.ok(response);
}
}