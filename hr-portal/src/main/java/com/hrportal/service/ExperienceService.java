package com.hrportal.service;

import com.hrportal.dto.ExperienceRequest;
import com.hrportal.entity.Experience;
import com.hrportal.entity.User;
import com.hrportal.exception.ExperienceNotFoundException;
import com.hrportal.exception.UserNotFoundException;
import com.hrportal.repository.ExperienceRepository;
import com.hrportal.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExperienceService {

    private final ExperienceRepository experienceRepository;
    private final UserRepository userRepository;

    public ExperienceService(ExperienceRepository experienceRepository, UserRepository userRepository) {
        this.experienceRepository = experienceRepository;
        this.userRepository = userRepository;
    }

    public Experience create(ExperienceRequest request) {

        Experience experience = new Experience();
        applyRequest(experience, request);
        return experienceRepository.save(experience);
    }

    public List<Experience> getAll() {
        return experienceRepository.findAll();
    }

    public Experience getById(Long id) {
        return experienceRepository.findById(id)
                .orElseThrow(() -> new ExperienceNotFoundException(
                        "Experience not found with id: " + id
                ));
    }

    public Experience update(Long id, ExperienceRequest request) {

        Experience experience = getById(id);
        applyRequest(experience, request);
        return experienceRepository.save(experience);
    }

    public void delete(Long id) {

        Experience experience = getById(id);
        experienceRepository.delete(experience);
    }

    private void applyRequest(Experience experience, ExperienceRequest request) {

        User jobSeeker = userRepository.findById(request.getJobSeekerId())
                .orElseThrow(() -> new UserNotFoundException(
                        "User not found with id: " + request.getJobSeekerId()
                ));

        experience.setJobSeeker(jobSeeker);
        experience.setCompanyName(request.getCompanyName());
        experience.setJobTitle(request.getJobTitle());
        experience.setStartDate(request.getStartDate());
        experience.setEndDate(request.getEndDate());
        experience.setCurrent(request.isCurrent());
        experience.setDescription(request.getDescription());
    }
}
