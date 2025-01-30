package com.booleanuk.api.cinema.responses;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter

public class ErrorResponse extends Response<Map<String, String>>{

    public ErrorResponse(String status, Map<String, String> data) {
        super(status, data);

    }

    public void set(String message) {
        Map<String, String> reply = new HashMap<>();
        reply.put("message", message);
        this.data = reply;
    }
}