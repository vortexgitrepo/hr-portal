package com.hrportal.service;

import com.hrportal.dto.ApplicationRequest;
import com.hrportal.entity.Application;
import com.hrportal.exception.ApplicationNotFoundException;
import com.hrportal.exception.JobNotFoundException;
import com.hrportal.exception.ResourceAlreadyExistsException;
import com.hrportal.exception.ResumeNotFoundException;
import com.hrportal.exception.UserNotFoundException;
import com.hrportal.repository.ApplicationRepository;
import com.hrportal.repository.JobRepository;
import com.hrportal.repository.ResumeRepository;
import com.hrportal.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    private final ResumeRepository resumeRepository;

    public ApplicationService(ApplicationRepository applicationRepository,
                              JobRepository jobRepository,
                              UserRepository userRepository,
                              ResumeRepository resumeRepository) {
        this.applicationRepository = applicationRepository;
        this.jobRepository = jobRepository;
        this.userRepository = userRepository;
        this.resumeRepository = resumeRepository;
    }

    public Application create(ApplicationRequest request) {

        if (applicationRepository.existsByJob_IdAndJobSeeker_Id(
                request.getJobId(), request.getJobSeekerId())) {
            throw new ResourceAlreadyExistsException("User has already applied to this job");
        }

        Application application = new Application();
        applyRequest(application, request);
        return applicationRepository.save(application);
    }

    public List<Application> getAll() {
        return applicationRepository.findAll();
    }

    public Application getById(Long id) {
        return applicationRepository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException(
                        "Application not found with id: " + id
                ));
    }

    public Application update(Long id, ApplicationRequest request) {

        Application application = getById(id);

        if (applicationRepository.existsByJob_IdAndJobSeeker_IdAndIdNot(
                request.getJobId(), request.getJobSeekerId(), id)) {
            throw new ResourceAlreadyExistsException("User has already applied to this job");
        }

        applyRequest(application, request);
        return applicationRepository.save(application);
    }

    public void delete(Long id) {

        Application application = getById(id);
        applicationRepository.delete(application);
    }

    private void applyRequest(Application application, ApplicationRequest request) {

        application.setJob(jobRepository.findById(request.getJobId())
                .orElseThrow(() -> new JobNotFoundException(
                        "Job not found with id: " + request.getJobId()
                )));

        application.setJobSeeker(userRepository.findById(request.getJobSeekerId())
                .orElseThrow(() -> new UserNotFoundException(
                        "User not found with id: " + request.getJobSeekerId()
                )));

        if (request.getResumeId() != null) {
            application.setResume(resumeRepository.findById(request.getResumeId())
                    .orElseThrow(() -> new ResumeNotFoundException(
                            "Resume not found with id: " + request.getResumeId()
                    )));
        } else {
            application.setResume(null);
        }

        if (request.getStatus() != null) {
            application.setStatus(request.getStatus());
        }
    }
}
