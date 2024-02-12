package com.booleanuk.api.cinema.response;

public class NotFoundResponse extends Response{

    public NotFoundResponse() {
        super("{\"message\": \"not found\"\n}", "error");
    }
}
