package com.booleanuk.api.cinema.util;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class SuccessResponse<T> {

    private String status;
    private T data;



    public SuccessResponse(
            T data
    ) {
        this.status = "success";
        this.data = data;
    }


}
