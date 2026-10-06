package com.hrportal.service;

import com.hrportal.dto.SavedJobRequest;
import com.hrportal.entity.SavedJob;
import com.hrportal.exception.JobNotFoundException;
import com.hrportal.exception.ResourceAlreadyExistsException;
import com.hrportal.exception.SavedJobNotFoundException;
import com.hrportal.exception.UserNotFoundException;
import com.hrportal.repository.JobRepository;
import com.hrportal.repository.SavedJobRepository;
import com.hrportal.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SavedJobService {

    private final SavedJobRepository savedJobRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;

    public SavedJobService(SavedJobRepository savedJobRepository,
                           JobRepository jobRepository,
                           UserRepository userRepository) {
        this.savedJobRepository = savedJobRepository;
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
    }

    public SavedJob create(SavedJobRequest request) {

        if (savedJobRepository.existsByJob_IdAndUser_Id(request.getJobId(), request.getUserId())) {
            throw new ResourceAlreadyExistsException("Job already saved by this user");
        }

        SavedJob savedJob = new SavedJob();
        applyRequest(savedJob, request);
        return savedJobRepository.save(savedJob);
    }

    public List<SavedJob> getAll() {
        return savedJobRepository.findAll();
    }

    public SavedJob getById(Long id) {
        return savedJobRepository.findById(id)
                .orElseThrow(() -> new SavedJobNotFoundException(
                        "Saved job not found with id: " + id
                ));
    }

    public SavedJob update(Long id, SavedJobRequest request) {

        SavedJob savedJob = getById(id);

        if (savedJobRepository.existsByJob_IdAndUser_IdAndIdNot(request.getJobId(), request.getUserId(), id)) {
            throw new ResourceAlreadyExistsException("Job already saved by this user");
        }

        applyRequest(savedJob, request);
        return savedJobRepository.save(savedJob);
    }

    public void delete(Long id) {

        SavedJob savedJob = getById(id);
        savedJobRepository.delete(savedJob);
    }

    private void applyRequest(SavedJob savedJob, SavedJobRequest request) {

        savedJob.setJob(jobRepository.findById(request.getJobId())
                .orElseThrow(() -> new JobNotFoundException(
                        "Job not found with id: " + request.getJobId()
                )));

        savedJob.setUser(userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException(
                        "User not found with id: " + request.getUserId()
                )));
    }
}
