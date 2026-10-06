package com.hrportal.service;

import com.hrportal.dto.ResumeRequest;
import com.hrportal.entity.Resume;
import com.hrportal.entity.User;
import com.hrportal.exception.ResumeNotFoundException;
import com.hrportal.exception.UserNotFoundException;
import com.hrportal.repository.ResumeRepository;
import com.hrportal.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResumeService {

    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;

    public ResumeService(ResumeRepository resumeRepository, UserRepository userRepository) {
        this.resumeRepository = resumeRepository;
        this.userRepository = userRepository;
    }

    public Resume create(ResumeRequest request) {

        Resume resume = new Resume();
        applyRequest(resume, request);
        return resumeRepository.save(resume);
    }

    public List<Resume> getAll() {
        return resumeRepository.findAll();
    }

    public Resume getById(Long id) {
        return resumeRepository.findById(id)
                .orElseThrow(() -> new ResumeNotFoundException(
                        "Resume not found with id: " + id
                ));
    }

    public Resume update(Long id, ResumeRequest request) {

        Resume resume = getById(id);
        applyRequest(resume, request);
        return resumeRepository.save(resume);
    }

    public void delete(Long id) {

        Resume resume = getById(id);
        resumeRepository.delete(resume);
    }

    private void applyRequest(Resume resume, ResumeRequest request) {

        User jobSeeker = userRepository.findById(request.getJobSeekerId())
                .orElseThrow(() -> new UserNotFoundException(
                        "User not found with id: " + request.getJobSeekerId()
                ));

        resume.setJobSeeker(jobSeeker);
        resume.setFileName(request.getFileName());
        resume.setResumeUrl(request.getResumeUrl());
        resume.setDefault(request.getIsDefault());
    }
}
