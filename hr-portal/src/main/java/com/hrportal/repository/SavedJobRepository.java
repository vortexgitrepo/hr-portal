package com.hrportal.repository;

import com.hrportal.entity.SavedJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SavedJobRepository extends JpaRepository<SavedJob, Long> {

    boolean existsByJob_IdAndUser_Id(Long jobId, Long userId);

    boolean existsByJob_IdAndUser_IdAndIdNot(Long jobId, Long userId, Long id);

    List<SavedJob> findByUserId(Long userId);
}
