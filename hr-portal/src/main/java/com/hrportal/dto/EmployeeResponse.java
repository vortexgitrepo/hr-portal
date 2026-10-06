package com.hrportal.dto;

import java.math.BigDecimal;

public class EmployeeResponse {

    private Long employeeId;
    private String name;
    private String email;
    private String phone;
    private String department;
    private String designation;
    private BigDecimal salary;
    private String message;

    public EmployeeResponse() {
    }

    public EmployeeResponse(
            Long employeeId,
            String name,
            String email,
            String phone,
            String department,
            String designation,
            BigDecimal salary,
            String message) {

        this.employeeId = employeeId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.department = department;
        this.designation = designation;
        this.salary = salary;
        this.message = message;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getDepartment() {
        return department;
    }

    public String getDesignation() {
        return designation;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public String getMessage() {
        return message;
    }
}
