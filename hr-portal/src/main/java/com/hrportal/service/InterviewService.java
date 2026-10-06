package com.hrportal.service;

import com.hrportal.dto.InterviewRequest;
import com.hrportal.entity.Application;
import com.hrportal.entity.Interview;
import com.hrportal.exception.ApplicationNotFoundException;
import com.hrportal.exception.InterviewNotFoundException;
import com.hrportal.repository.ApplicationRepository;
import com.hrportal.repository.InterviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterviewService {

    private final InterviewRepository interviewRepository;
    private final ApplicationRepository applicationRepository;

    public InterviewService(InterviewRepository interviewRepository,
                            ApplicationRepository applicationRepository) {
        this.interviewRepository = interviewRepository;
        this.applicationRepository = applicationRepository;
    }

    public Interview create(InterviewRequest request) {

        Interview interview = new Interview();
        applyRequest(interview, request);
        return interviewRepository.save(interview);
    }

    public List<Interview> getAll() {
        return interviewRepository.findAll();
    }

    public Interview getById(Long id) {
        return interviewRepository.findById(id)
                .orElseThrow(() -> new InterviewNotFoundException(
                        "Interview not found with id: " + id
                ));
    }

    public Interview update(Long id, InterviewRequest request) {

        Interview interview = getById(id);

        applyRequest(interview, request);
        return interviewRepository.save(interview);
    }

    public void delete(Long id) {

        Interview interview = getById(id);
        interviewRepository.delete(interview);
    }

    private void applyRequest(Interview interview, InterviewRequest request) {

        Application application = applicationRepository.findById(request.getApplicationId())
                .orElseThrow(() -> new ApplicationNotFoundException(
                        "Application not found with id: " + request.getApplicationId()
                ));

        interview.setApplication(application);
        interview.setInterviewDate(request.getInterviewDate());
        interview.setInterviewType(request.getInterviewType());
        interview.setMeetingLink(request.getMeetingLink());
        interview.setFeedback(request.getFeedback());

        if (request.getStatus() != null) {
            interview.setStatus(request.getStatus());
        }
    }
}
