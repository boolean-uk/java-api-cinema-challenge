package com.booleanuk.api.cinema.response;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ErrorResponse extends Response<Map<String, String>>{

    public void set(String message) {
        this.status = "error";
        Map<String, String> reply = new HashMap<>();
        reply.put("message", message);
        this.data = reply;
    }
}
