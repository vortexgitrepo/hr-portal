package com.hrportal.service;

import com.hrportal.dto.SkillRequest;
import com.hrportal.entity.Skill;
import com.hrportal.exception.ResourceAlreadyExistsException;
import com.hrportal.exception.SkillNotFoundException;
import com.hrportal.repository.SkillRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkillService {

    private final SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public Skill create(SkillRequest request) {

        if (skillRepository.existsByName(request.getName())) {
            throw new ResourceAlreadyExistsException("Skill already exists: " + request.getName());
        }

        Skill skill = new Skill();
        applyRequest(skill, request);
        return skillRepository.save(skill);
    }

    public List<Skill> getAll() {
        return skillRepository.findAll();
    }

    public Skill getById(Long id) {
        return skillRepository.findById(id)
                .orElseThrow(() -> new SkillNotFoundException(
                        "Skill not found with id: " + id
                ));
    }

    public Skill update(Long id, SkillRequest request) {

        Skill skill = getById(id);

        if (skillRepository.existsByNameAndIdNot(request.getName(), id)) {
            throw new ResourceAlreadyExistsException("Skill already exists: " + request.getName());
        }

        applyRequest(skill, request);
        return skillRepository.save(skill);
    }

    public void delete(Long id) {

        Skill skill = getById(id);
        skillRepository.delete(skill);
    }

    private void applyRequest(Skill skill, SkillRequest request) {
        skill.setName(request.getName());
    }
}
