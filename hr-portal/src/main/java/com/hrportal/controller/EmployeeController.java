package com.hrportal.controller;

import com.hrportal.dto.EmployeeRequest;
import com.hrportal.dto.EmployeeResponse;
import com.hrportal.entity.Employee;
import com.hrportal.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<EmployeeResponse> create(@Valid @RequestBody
                                                   EmployeeRequest request) {

        Employee employee = employeeService.create(request);

        EmployeeResponse response = toResponse(employee, "Employee created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponse>> getAll() {

        List<EmployeeResponse> employees = employeeService.getAll().stream()
                .map(employee -> toResponse(employee, null))
                .toList();

        return ResponseEntity.ok(employees);
    }

    @GetMapping("/department")
    public ResponseEntity<List<EmployeeResponse>> getByDepartment(
            @RequestParam String department) {

        List<EmployeeResponse> employees = employeeService.getByDepartment(department).stream()
                .map(employee -> toResponse(employee, null))
                .toList();

        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponse> getById(@PathVariable Long id) {

        Employee employee = employeeService.getById(id);

        EmployeeResponse response = toResponse(employee, "Employee fetched successfully");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponse> update(@PathVariable Long id,
                                                   @Valid @RequestBody
                                                   EmployeeRequest request) {

        Employee employee = employeeService.update(id, request);

        EmployeeResponse response = toResponse(employee, "Employee updated successfully");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EmployeeResponse> delete(@PathVariable Long id) {

        Employee employee = employeeService.getById(id);
        employeeService.delete(id);

        EmployeeResponse response = toResponse(employee, "Employee deleted successfully");
        return ResponseEntity.ok(response);
    }

    private EmployeeResponse toResponse(Employee employee, String message) {
        return new EmployeeResponse(
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                employee.getPhone(),
                employee.getDepartment(),
                employee.getDesignation(),
                employee.getSalary(),
                message
        );
    }
}
