package com.hrportal.controller;

import com.hrportal.dto.CompanyRequest;
import com.hrportal.dto.CompanyResponse;
import com.hrportal.entity.Company;
import com.hrportal.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping
    public ResponseEntity<CompanyResponse> create(@Valid @RequestBody
                                                  CompanyRequest request) {

        Company company = companyService.create(request);

        CompanyResponse response = toResponse(company, "Company created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<CompanyResponse>> getAll() {

        List<CompanyResponse> companies = companyService.getAll().stream()
                .map(company -> toResponse(company, null))
                .toList();

        return ResponseEntity.ok(companies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponse> getById(@PathVariable Long id) {

        Company company = companyService.getById(id);

        CompanyResponse response = toResponse(company, "Company fetched successfully");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyResponse> update(@PathVariable Long id,
                                                  @Valid @RequestBody
                                                  CompanyRequest request) {

        Company company = companyService.update(id, request);

        CompanyResponse response = toResponse(company, "Company updated successfully");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CompanyResponse> delete(@PathVariable Long id) {

        Company company = companyService.getById(id);
        companyService.delete(id);

        CompanyResponse response = toResponse(company, "Company deleted successfully");
        return ResponseEntity.ok(response);
    }

    private CompanyResponse toResponse(Company company, String message) {
        return new CompanyResponse(
                company.getId(),
                company.getName(),
                company.getDescription(),
                company.getWebsite(),
                company.getIndustry(),
                company.getLocation(),
                company.getCompanySize(),
                company.getLogoUrl(),
                company.getOwner() != null ? company.getOwner().getId() : null,
                company.getCreatedAt(),
                company.getUpdatedAt(),
                message
        );
    }
}
