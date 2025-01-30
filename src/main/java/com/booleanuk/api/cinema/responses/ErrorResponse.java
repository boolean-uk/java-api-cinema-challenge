package com.booleanuk.api.cinema.responses;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter

public class ErrorResponse extends Response<String>{

    public ErrorResponse(String data) {
        super(data);
        this.status = "Error";

    }
}