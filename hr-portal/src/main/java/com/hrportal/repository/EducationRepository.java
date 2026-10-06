package com.hrportal.repository;

import com.hrportal.entity.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EducationRepository extends JpaRepository<Education, Long> {

    List<Education> findByJobSeeker_Id(Long jobSeekerId);
}
