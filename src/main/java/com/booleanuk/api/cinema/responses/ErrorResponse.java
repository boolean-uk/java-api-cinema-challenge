package com.booleanuk.api.cinema.responses;

import java.util.HashMap;
import java.util.Map;

public class ErrorResponse extends Response<HashMap<String, String>>{

    public void set(String message) {
        this.status = "error";
        HashMap<String, String> response = new HashMap<>();
        response.put("message", message);
        this.data = response;
    }
}
