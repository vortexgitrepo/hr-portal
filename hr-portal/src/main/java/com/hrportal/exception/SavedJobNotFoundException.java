package com.hrportal.exception;

public class SavedJobNotFoundException extends ResourceNotFoundException {

    public SavedJobNotFoundException(String message) {
        super(message);
    }
}
