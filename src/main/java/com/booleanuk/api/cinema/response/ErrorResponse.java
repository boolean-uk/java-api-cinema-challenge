package com.booleanuk.api.cinema.response;

public class ErrorResponse extends Response<Error> {
    public ErrorResponse(Error data) {
        super("error", data);
    }
}
