package com.booleanuk.api.cinema.models;

import java.util.List;

public class CustomResponse<T> {
    private final String status;
    private final T data;

    public CustomResponse(T data) {
        this.status = "success";
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }
}
