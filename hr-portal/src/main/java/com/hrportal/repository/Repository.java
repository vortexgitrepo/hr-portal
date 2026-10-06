package com.hrportal.repository;

import com.hrportal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

@org.springframework.stereotype.Repository
public interface Repository extends JpaRepository<User,Long> {
}
