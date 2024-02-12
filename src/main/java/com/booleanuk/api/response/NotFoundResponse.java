package com.booleanuk.api.response;

public class NotFoundResponse extends ResponseTemplate{
    public NotFoundResponse() {
        super("error", new ErrorMessage("not found"));
    }
}
