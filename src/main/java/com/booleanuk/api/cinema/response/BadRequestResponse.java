package com.booleanuk.api.cinema.response;

public class BadRequestResponse extends Response {

    public BadRequestResponse() {
        super(new Error("Bad request, please check all required fields are correct"));
        this.status = "error";
    }
}
