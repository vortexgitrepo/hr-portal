package com.hrportal.repository;

import com.hrportal.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    boolean existsByJob_IdAndJobSeeker_Id(Long jobId, Long jobSeekerId);

    boolean existsByJob_IdAndJobSeeker_IdAndIdNot(Long jobId, Long jobSeekerId, Long id);

    List<Application> findByJobSeeker_Id(Long jobSeekerId);

    List<Application> findByJob_Id(Long jobId);
}
