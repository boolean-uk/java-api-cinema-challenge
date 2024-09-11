package com.booleanuk.api.cinema.respons_handling;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseCreator <T>{
    private String status;
    private T data;

    public ResponseCreator(String status, T data) {
        this.status = status;
        this.data = data;
    }


}
