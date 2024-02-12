package com.booleanuk.api.cinema.model;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Error {

    @JsonProperty("message")
    private String message;

    public Error(String message) {
        this.message = message;
    }
}
