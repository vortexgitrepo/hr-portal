package com.hrportal.service;

import com.hrportal.dto.ApplicationStatusHistoryRequest;
import com.hrportal.entity.Application;
import com.hrportal.entity.ApplicationStatusHistory;
import com.hrportal.exception.ApplicationNotFoundException;
import com.hrportal.exception.ApplicationStatusHistoryNotFoundException;
import com.hrportal.repository.ApplicationRepository;
import com.hrportal.repository.ApplicationStatusHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationStatusHistoryService {

    private final ApplicationStatusHistoryRepository applicationStatusHistoryRepository;
    private final ApplicationRepository applicationRepository;

    public ApplicationStatusHistoryService(
            ApplicationStatusHistoryRepository applicationStatusHistoryRepository,
            ApplicationRepository applicationRepository) {
        this.applicationStatusHistoryRepository = applicationStatusHistoryRepository;
        this.applicationRepository = applicationRepository;
    }

    public ApplicationStatusHistory create(ApplicationStatusHistoryRequest request) {

        ApplicationStatusHistory applicationStatusHistory = new ApplicationStatusHistory();
        applyRequest(applicationStatusHistory, request);
        return applicationStatusHistoryRepository.save(applicationStatusHistory);
    }

    public List<ApplicationStatusHistory> getAll() {
        return applicationStatusHistoryRepository.findAll();
    }

    public ApplicationStatusHistory getById(Long id) {
        return applicationStatusHistoryRepository.findById(id)
                .orElseThrow(() -> new ApplicationStatusHistoryNotFoundException(
                        "Application status history not found with id: " + id
                ));
    }

    public ApplicationStatusHistory update(Long id, ApplicationStatusHistoryRequest request) {

        ApplicationStatusHistory applicationStatusHistory = getById(id);

        applyRequest(applicationStatusHistory, request);
        return applicationStatusHistoryRepository.save(applicationStatusHistory);
    }

    public void delete(Long id) {

        ApplicationStatusHistory applicationStatusHistory = getById(id);
        applicationStatusHistoryRepository.delete(applicationStatusHistory);
    }

    private void applyRequest(ApplicationStatusHistory applicationStatusHistory,
                              ApplicationStatusHistoryRequest request) {

        Application application = applicationRepository.findById(request.getApplicationId())
                .orElseThrow(() -> new ApplicationNotFoundException(
                        "Application not found with id: " + request.getApplicationId()
                ));

        applicationStatusHistory.setApplication(application);
        applicationStatusHistory.setStatus(request.getStatus());
        applicationStatusHistory.setComment(request.getComment());
    }
}
