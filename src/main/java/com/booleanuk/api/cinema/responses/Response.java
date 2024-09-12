package com.booleanuk.api.cinema.responses;

import lombok.Getter;

@Getter
public class Response<T> {
    protected String status;
    protected Object data;

    public void setSuccess(T data) {
        this.status = "success";
        this.data = data;
    }

    public void setError(String errorMessage) {
        this.status = "error";
        this.data = new ErrorData(errorMessage);
    }

    private record ErrorData(String message) {
    }
}