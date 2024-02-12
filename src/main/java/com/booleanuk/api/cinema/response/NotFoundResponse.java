package com.booleanuk.api.cinema.response;

public class NotFoundResponse extends Response{

    //TODO: fix
    public NotFoundResponse() {
        super("{\"message\": \"not found\"\n}", "error");
    }
}
