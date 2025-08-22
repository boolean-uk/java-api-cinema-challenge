package com.booleanuk.api.cinema.DTO;


public class ErrorData {
    private String message;

    // Constructor, getters, and setters
    public ErrorData(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
