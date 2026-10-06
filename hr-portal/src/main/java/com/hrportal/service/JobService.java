package com.hrportal.service;

import com.hrportal.dto.JobRequest;
import com.hrportal.entity.Company;
import com.hrportal.entity.Job;
import com.hrportal.entity.Skill;
import com.hrportal.exception.CompanyNotFoundException;
import com.hrportal.exception.JobNotFoundException;
import com.hrportal.exception.ResourceNotFoundException;
import com.hrportal.repository.CompanyRepository;
import com.hrportal.repository.JobRepository;
import com.hrportal.repository.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class JobService {

    private final JobRepository jobRepository;
    private final CompanyRepository companyRepository;
    private final SkillRepository skillRepository;

    public JobService(JobRepository jobRepository,
                      CompanyRepository companyRepository,
                      SkillRepository skillRepository) {
        this.jobRepository = jobRepository;
        this.companyRepository = companyRepository;
        this.skillRepository = skillRepository;
    }

    public Job create(JobRequest request) {

        Job job = new Job();
        applyRequest(job, request);
        return jobRepository.save(job);
    }

    public List<Job> getAll() {
        return jobRepository.findAll();
    }

    public Job getById(Long id) {
        return jobRepository.findById(id)
                .orElseThrow(() -> new JobNotFoundException(
                        "Job not found with id: " + id
                ));
    }

    public Job update(Long id, JobRequest request) {

        Job job = getById(id);

        applyRequest(job, request);
        return jobRepository.save(job);
    }

    public void delete(Long id) {

        Job job = getById(id);
        jobRepository.delete(job);
    }

    private void applyRequest(Job job, JobRequest request) {

        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new CompanyNotFoundException(
                        "Company not found with id: " + request.getCompanyId()
                ));

        job.setTitle(request.getTitle());
        job.setDescription(request.getDescription());
        job.setCompany(company);
        job.setLocation(request.getLocation());
        job.setEmploymentType(request.getEmploymentType());
        job.setExperienceMin(request.getExperienceMin());
        job.setExperienceMax(request.getExperienceMax());
        job.setSalaryMin(request.getSalaryMin());
        job.setSalaryMax(request.getSalaryMax());
        job.setSkills(loadSkills(request.getSkillIds()));

        if (request.getStatus() != null) {
            job.setStatus(request.getStatus());
        }
    }

    private List<Skill> loadSkills(List<Long> skillIds) {

        if (skillIds == null || skillIds.isEmpty()) {
            return new ArrayList<>();
        }

        List<Skill> skills = new ArrayList<>(skillRepository.findAllById(skillIds));

        Set<Long> foundIds = new HashSet<>();
        for (Skill skill : skills) {
            foundIds.add(skill.getId());
        }

        for (Long skillId : skillIds) {
            if (!foundIds.contains(skillId)) {
                throw new ResourceNotFoundException("Skill not found with id: " + skillId);
            }
        }

        return skills;
    }
}
