package com.booleanuk.api.cinema.response;

import java.util.HashMap;

public class ErrorResponse extends Response<HashMap<String, String>>{

    public ErrorResponse(String data) {
        super("error", new HashMap<>(){{put("message", data);}});
    }
}
