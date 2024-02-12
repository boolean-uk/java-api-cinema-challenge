package com.booleanuk.api.cinema.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {
    String status;
    Object data;

    public Response(Object data) {
        this.data = data;
    }

}
