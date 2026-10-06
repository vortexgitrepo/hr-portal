package com.hrportal.controller;

import com.hrportal.dto.SkillRequest;
import com.hrportal.dto.SkillResponse;
import com.hrportal.entity.Skill;
import com.hrportal.service.SkillService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
public class SkillController {

    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @PostMapping
    public ResponseEntity<SkillResponse> create(@Valid @RequestBody
                                                SkillRequest request) {

        Skill skill = skillService.create(request);

        SkillResponse response = toResponse(skill, "Skill created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<SkillResponse>> getAll() {

        List<SkillResponse> skills = skillService.getAll().stream()
                .map(skill -> toResponse(skill, null))
                .toList();

        return ResponseEntity.ok(skills);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SkillResponse> getById(@PathVariable Long id) {

        Skill skill = skillService.getById(id);

        SkillResponse response = toResponse(skill, "Skill fetched successfully");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SkillResponse> update(@PathVariable Long id,
                                                @Valid @RequestBody
                                                SkillRequest request) {

        Skill skill = skillService.update(id, request);

        SkillResponse response = toResponse(skill, "Skill updated successfully");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SkillResponse> delete(@PathVariable Long id) {

        Skill skill = skillService.getById(id);
        skillService.delete(id);

        SkillResponse response = toResponse(skill, "Skill deleted successfully");
        return ResponseEntity.ok(response);
    }

    private SkillResponse toResponse(Skill skill, String message) {
        return new SkillResponse(
                skill.getId(),
                skill.getName(),
                message
        );
    }
}
