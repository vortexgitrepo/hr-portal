package com.hrportal.exception;

public class JobNotFoundException extends ResourceNotFoundException {

    public JobNotFoundException(String message) {
        super(message);
    }
}
