package com.booleanuk.api.helpers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ResponseHandler {
    public static ResponseEntity<Object> generateResponse(HttpStatus status, Object responseObj) {

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("status", status.getReasonPhrase().toLowerCase());
        map.put("data", responseObj);
        return new ResponseEntity<>(map, status);
    }


    public static ResponseEntity<Object> generateError(HttpStatus status){
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("status", "error");
        map.put("data", new MessageResponse(status.getReasonPhrase()));
        return new ResponseEntity<>(map, status);
    }

    private record MessageResponse(String message) {}
}