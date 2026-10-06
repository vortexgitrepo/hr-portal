package com.hrportal.repository;

import com.hrportal.entity.JobSeekerProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobSeekerProfileRepository extends JpaRepository<JobSeekerProfile, Long> {

    boolean existsByUser_Id(Long userId);

    boolean existsByUser_IdAndIdNot(Long userId, Long id);
}
