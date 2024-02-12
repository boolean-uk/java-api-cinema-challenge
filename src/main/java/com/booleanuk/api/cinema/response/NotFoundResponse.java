package com.booleanuk.api.cinema.response;

import com.booleanuk.api.cinema.model.Error;

public class NotFoundResponse extends Response{

    //TODO: fix
    public NotFoundResponse() {
        super(new Error("not found"), "error");
    }
}
