package com.hrportal.service;

import com.hrportal.dto.JobSeekerProfileRequest;
import com.hrportal.entity.JobSeekerProfile;
import com.hrportal.entity.User;
import com.hrportal.exception.JobSeekerProfileNotFoundException;
import com.hrportal.exception.ResourceAlreadyExistsException;
import com.hrportal.exception.UserNotFoundException;
import com.hrportal.repository.JobSeekerProfileRepository;
import com.hrportal.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobSeekerProfileService {

    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final UserRepository userRepository;

    public JobSeekerProfileService(JobSeekerProfileRepository jobSeekerProfileRepository,
                                   UserRepository userRepository) {
        this.jobSeekerProfileRepository = jobSeekerProfileRepository;
        this.userRepository = userRepository;
    }

    public JobSeekerProfile create(JobSeekerProfileRequest request) {

        if (jobSeekerProfileRepository.existsByUser_Id(request.getUserId())) {
            throw new ResourceAlreadyExistsException("Profile already exists for this user");
        }

        JobSeekerProfile jobSeekerProfile = new JobSeekerProfile();
        applyRequest(jobSeekerProfile, request);
        return jobSeekerProfileRepository.save(jobSeekerProfile);
    }

    public List<JobSeekerProfile> getAll() {
        return jobSeekerProfileRepository.findAll();
    }

    public JobSeekerProfile getById(Long id) {
        return jobSeekerProfileRepository.findById(id)
                .orElseThrow(() -> new JobSeekerProfileNotFoundException(
                        "JobSeekerProfile not found with id: " + id
                ));
    }

    public JobSeekerProfile update(Long id, JobSeekerProfileRequest request) {

        JobSeekerProfile jobSeekerProfile = getById(id);

        if (jobSeekerProfileRepository.existsByUser_IdAndIdNot(request.getUserId(), id)) {
            throw new ResourceAlreadyExistsException("Profile already exists for this user");
        }

        applyRequest(jobSeekerProfile, request);
        return jobSeekerProfileRepository.save(jobSeekerProfile);
    }

    public void delete(Long id) {

        JobSeekerProfile jobSeekerProfile = getById(id);
        jobSeekerProfileRepository.delete(jobSeekerProfile);
    }

    private void applyRequest(JobSeekerProfile jobSeekerProfile, JobSeekerProfileRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException(
                        "User not found with id: " + request.getUserId()
                ));

        jobSeekerProfile.setUser(user);
        jobSeekerProfile.setHeadline(request.getHeadline());
        jobSeekerProfile.setSummary(request.getSummary());
        jobSeekerProfile.setExperienceYears(request.getExperienceYears());
        jobSeekerProfile.setCurrentCompany(request.getCurrentCompany());
        jobSeekerProfile.setCurrentLocation(request.getCurrentLocation());
        jobSeekerProfile.setPreferredLocation(request.getPreferredLocation());
        jobSeekerProfile.setExpectedSalary(request.getExpectedSalary());
        jobSeekerProfile.setNoticePeriod(request.getNoticePeriod());
        jobSeekerProfile.setProfileImageUrl(request.getProfileImageUrl());
    }
}
