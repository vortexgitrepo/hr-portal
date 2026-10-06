package com.hrportal.exception;

public class CompanyNotFoundException extends ResourceNotFoundException {

    public CompanyNotFoundException(String message) {
        super(message);
    }
}
