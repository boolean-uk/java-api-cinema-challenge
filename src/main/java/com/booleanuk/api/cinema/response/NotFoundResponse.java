package com.booleanuk.api.cinema.response;

import com.booleanuk.api.cinema.model.Error;

public class NotFoundResponse extends Response<Error>{

    public NotFoundResponse() {
        super("error", new Error("not found"));
    }
}
