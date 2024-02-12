package com.booleanuk.api.cinema.response;

public class BadRequestResponse extends Response{

    public BadRequestResponse() {
        super("{\"message\": \"bad request\"\n}", "error");
    }
}
