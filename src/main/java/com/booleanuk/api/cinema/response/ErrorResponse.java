package com.booleanuk.api.cinema.response;

import java.util.HashMap;
import java.util.Map;

public class ErrorResponse implements ResponseInterface {
    private final String status;
    private final Map<String, String> data;

    public ErrorResponse(String message) {
        this.status = "error";
        this.data = new HashMap<>();
        this.data.put("message", message);
    }

    @Override
    public String getStatus() {
        return this.status;
    }

    @Override
    public Object getData() {
        return this.data;
    }
}
