package com.hrportal.service;

import com.hrportal.dto.EducationRequest;
import com.hrportal.entity.Education;
import com.hrportal.entity.User;
import com.hrportal.exception.EducationNotFoundException;
import com.hrportal.exception.UserNotFoundException;
import com.hrportal.repository.EducationRepository;
import com.hrportal.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EducationService {

    private final EducationRepository educationRepository;
    private final UserRepository userRepository;

    public EducationService(EducationRepository educationRepository, UserRepository userRepository) {
        this.educationRepository = educationRepository;
        this.userRepository = userRepository;
    }

    public Education create(EducationRequest request) {

        Education education = new Education();
        applyRequest(education, request);
        return educationRepository.save(education);
    }

    public List<Education> getAll() {
        return educationRepository.findAll();
    }

    public Education getById(Long id) {
        return educationRepository.findById(id)
                .orElseThrow(() -> new EducationNotFoundException(
                        "Education not found with id: " + id
                ));
    }

    public Education update(Long id, EducationRequest request) {

        Education education = getById(id);
        applyRequest(education, request);
        return educationRepository.save(education);
    }

    public void delete(Long id) {

        Education education = getById(id);
        educationRepository.delete(education);
    }

    private void applyRequest(Education education, EducationRequest request) {

        User jobSeeker = userRepository.findById(request.getJobSeekerId())
                .orElseThrow(() -> new UserNotFoundException(
                        "User not found with id: " + request.getJobSeekerId()
                ));

        education.setJobSeeker(jobSeeker);
        education.setDegree(request.getDegree());
        education.setInstitution(request.getInstitution());
        education.setFieldOfStudy(request.getFieldOfStudy());
        education.setStartYear(request.getStartYear());
        education.setEndYear(request.getEndYear());
        education.setPercentage(request.getPercentage());
    }
}
