package com.booleanuk.api.response;

public class BadRequestResponse extends ResponseTemplate{

    public BadRequestResponse() {
        super("error", new ErrorMessage("bad request"));
    }
}
