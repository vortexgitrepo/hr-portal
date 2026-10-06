package com.hrportal.controller;

import com.hrportal.dto.JobSeekerProfileRequest;
import com.hrportal.dto.JobSeekerProfileResponse;
import com.hrportal.entity.JobSeekerProfile;
import com.hrportal.service.JobSeekerProfileService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-seeker-profiles")
public class JobSeekerProfileController {

    private final JobSeekerProfileService jobSeekerProfileService;

    public JobSeekerProfileController(JobSeekerProfileService jobSeekerProfileService) {
        this.jobSeekerProfileService = jobSeekerProfileService;
    }

    @PostMapping
    public ResponseEntity<JobSeekerProfileResponse> create(@Valid @RequestBody
                                                           JobSeekerProfileRequest request) {

        JobSeekerProfile jobSeekerProfile = jobSeekerProfileService.create(request);

        JobSeekerProfileResponse response =
                toResponse(jobSeekerProfile, "JobSeekerProfile created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<JobSeekerProfileResponse>> getAll() {

        List<JobSeekerProfileResponse> profiles = jobSeekerProfileService.getAll().stream()
                .map(profile -> toResponse(profile, null))
                .toList();

        return ResponseEntity.ok(profiles);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobSeekerProfileResponse> getById(@PathVariable Long id) {

        JobSeekerProfile jobSeekerProfile = jobSeekerProfileService.getById(id);

        JobSeekerProfileResponse response =
                toResponse(jobSeekerProfile, "JobSeekerProfile fetched successfully");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobSeekerProfileResponse> update(@PathVariable Long id,
                                                           @Valid @RequestBody
                                                           JobSeekerProfileRequest request) {

        JobSeekerProfile jobSeekerProfile = jobSeekerProfileService.update(id, request);

        JobSeekerProfileResponse response =
                toResponse(jobSeekerProfile, "JobSeekerProfile updated successfully");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<JobSeekerProfileResponse> delete(@PathVariable Long id) {

        JobSeekerProfile jobSeekerProfile = jobSeekerProfileService.getById(id);
        jobSeekerProfileService.delete(id);

        JobSeekerProfileResponse response =
                toResponse(jobSeekerProfile, "JobSeekerProfile deleted successfully");
        return ResponseEntity.ok(response);
    }

    private JobSeekerProfileResponse toResponse(JobSeekerProfile profile, String message) {
        return new JobSeekerProfileResponse(
                profile.getId(),
                profile.getUser().getId(),
                profile.getHeadline(),
                profile.getSummary(),
                profile.getExperienceYears(),
                profile.getCurrentCompany(),
                profile.getCurrentLocation(),
                profile.getPreferredLocation(),
                profile.getExpectedSalary(),
                profile.getNoticePeriod(),
                profile.getProfileImageUrl(),
                message
        );
    }
}
