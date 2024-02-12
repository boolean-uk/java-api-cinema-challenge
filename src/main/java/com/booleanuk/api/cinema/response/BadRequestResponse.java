package com.booleanuk.api.cinema.response;

import com.booleanuk.api.cinema.model.Error;

public class BadRequestResponse extends Response<Error>{

    public BadRequestResponse() {
        super("error", new Error("bad request"));
    }
}
