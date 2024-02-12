package com.booleanuk.api.cinema.response;

import com.booleanuk.api.cinema.model.Error;

public class BadRequestResponse extends Response{

    public BadRequestResponse() {
        super(new Error("bad request"), "error");
    }
}
