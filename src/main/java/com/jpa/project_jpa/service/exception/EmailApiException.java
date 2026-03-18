package com.jpa.project_jpa.service.exception;


public class EmailApiException extends RuntimeException {
    public EmailApiException() {
        super("Error sending email...");
    }
}