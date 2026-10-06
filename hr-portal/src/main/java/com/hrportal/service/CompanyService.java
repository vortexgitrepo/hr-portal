package com.hrportal.service;

import com.hrportal.dto.CompanyRequest;
import com.hrportal.entity.Company;
import com.hrportal.entity.User;
import com.hrportal.exception.CompanyNotFoundException;
import com.hrportal.exception.UserNotFoundException;
import com.hrportal.repository.CompanyRepository;
import com.hrportal.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    public CompanyService(CompanyRepository companyRepository, UserRepository userRepository) {
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }

    public Company create(CompanyRequest request) {

        Company company = new Company();
        applyRequest(company, request);
        return companyRepository.save(company);
    }

    public List<Company> getAll() {
        return companyRepository.findAll();
    }

    public Company getById(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new CompanyNotFoundException(
                        "Company not found with id: " + id
                ));
    }

    public Company update(Long id, CompanyRequest request) {

        Company company = getById(id);

        applyRequest(company, request);
        return companyRepository.save(company);
    }

    public void delete(Long id) {

        Company company = getById(id);
        companyRepository.delete(company);
    }

    private void applyRequest(Company company, CompanyRequest request) {
        company.setName(request.getName());
        company.setDescription(request.getDescription());
        company.setWebsite(request.getWebsite());
        company.setIndustry(request.getIndustry());
        company.setLocation(request.getLocation());
        company.setCompanySize(request.getCompanySize());
        company.setLogoUrl(request.getLogoUrl());

        if (request.getOwnerId() != null) {
            User owner = userRepository.findById(request.getOwnerId())
                    .orElseThrow(() -> new UserNotFoundException(
                            "User not found with id: " + request.getOwnerId()
                    ));
            company.setOwner(owner);
        } else {
            company.setOwner(null);
        }
    }
}
