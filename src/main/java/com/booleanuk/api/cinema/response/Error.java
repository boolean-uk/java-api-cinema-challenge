package com.booleanuk.api.cinema.response;

import lombok.Getter;

@Getter
public class Error {
    private String message;

    public Error (String message) {
        this.message = message;
    }
}
