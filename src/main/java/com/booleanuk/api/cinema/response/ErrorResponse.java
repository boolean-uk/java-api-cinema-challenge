package com.booleanuk.api.cinema.response;

import java.util.HashMap;
import java.util.Map;

public class ErrorResponse extends Response<Map<String, String>> {


    public void set(String message) {
        this.status = "error";
        Map<String, String> reply = new HashMap<>();
        reply.put("message", message);

    }
}
