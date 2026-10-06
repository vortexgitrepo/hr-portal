package com.hrportal.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @GetMapping("/hello")
    public ResponseEntity<Map<String, Object>> hello(Authentication authentication) {
        return ResponseEntity.ok(Map.of(
                "message", "JWT token is valid!",
                "authenticatedAs", authentication.getName()
        ));
    }
}
