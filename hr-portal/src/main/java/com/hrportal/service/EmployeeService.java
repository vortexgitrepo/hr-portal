package com.hrportal.service;

import com.hrportal.dto.EmployeeRequest;
import com.hrportal.entity.Employee;
import com.hrportal.exception.EmailAlreadyExistsException;
import com.hrportal.exception.EmployeeNotFoundException;
import com.hrportal.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee create(EmployeeRequest request) {

        if (employeeRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already registered");
        }

        Employee employee = new Employee();
        applyRequest(employee, request);
        return employeeRepository.save(employee);
    }

    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    public List<Employee> getByDepartment(String department) {
        return employeeRepository.findByDepartmentIgnoreCase(department);
    }

    public Employee getById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(
                        "Employee not found with id: " + id
                ));
    }

    public Employee update(Long id, EmployeeRequest request) {

        Employee employee = getById(id);

        if (employeeRepository.existsByEmailAndIdNot(request.getEmail(), id)) {
            throw new EmailAlreadyExistsException("Email already registered");
        }

        applyRequest(employee, request);
        return employeeRepository.save(employee);
    }

    public void delete(Long id) {

        Employee employee = getById(id);
        employeeRepository.delete(employee);
    }

    private void applyRequest(Employee employee, EmployeeRequest request) {
        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setPhone(request.getPhone());
        employee.setDepartment(request.getDepartment());
        employee.setDesignation(request.getDesignation());
        employee.setSalary(request.getSalary());
    }
}
