package com.booleanuk.api.cinema.response;

public class NotFoundResponse extends Response{

    public NotFoundResponse() {
        super(new Error("The id was not found"));
        this.status = "error";
    }
}
