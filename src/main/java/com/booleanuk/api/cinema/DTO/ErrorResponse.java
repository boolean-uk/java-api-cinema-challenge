package com.booleanuk.api.cinema.DTO;

public class ErrorResponse {
    private String status;
    private ErrorData data;

    // No-argument constructor
    public ErrorResponse() {}

    // Parameterized constructor
    public ErrorResponse(String status, ErrorData data) {
        this.status = status;
        this.data = data;
    }

    // Getters and setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ErrorData getData() {
        return data;
    }

    public void setData(ErrorData data) {
        this.data = data;
    }
}
