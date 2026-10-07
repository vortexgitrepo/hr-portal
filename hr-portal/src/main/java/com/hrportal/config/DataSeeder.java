package com.hrportal.config;

import com.hrportal.entity.User;
import com.hrportal.enums.Role;
import com.hrportal.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataSeeder {

    @Bean
    public CommandLineRunner seedAdminUser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (!userRepository.existsByEmail("admin@hrportal.com")) {
                User admin = new User();
                admin.setName("Admin");
                admin.setEmail("admin@hrportal.com");
                admin.setPassword(passwordEncoder.encode("admin12345"));
                admin.setRole(Role.ADMIN);
                userRepository.save(admin);
                System.out.println("Admin user created: admin@hrportal.com / admin12345");
            }
        };
    }
}
