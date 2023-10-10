package com.booleanuk.api.cinema.responses;

import java.util.List;
import java.util.Map;

import com.booleanuk.api.cinema.enums.ResponseStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.HashMap;

@Getter
@NoArgsConstructor
public class ErrorResponse extends Response<Map<String, ?>>{
    public void set(String message) {
        this.status = ResponseStatus.ERROR;
        Map<String, String> reply = new HashMap<>();
        reply.put("message", message);
        this.data = reply;
    }
    public void set(List errors) {
        this.status = ResponseStatus.ERROR;
        Map<String, List> reply = new HashMap<>();
        reply.put("message", errors);
        this.data = reply;
    }
}