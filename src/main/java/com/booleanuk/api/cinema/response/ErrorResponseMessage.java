package com.booleanuk.api.cinema.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponseMessage {

    private String message;

    public ErrorResponseMessage(String message) {
        this.message = message;
    }
}