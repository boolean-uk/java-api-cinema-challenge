package com.booleanuk.api.cinema;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
public class ApiResponse<T> {
    protected String status;
    protected T data;
    public void set(T data){
        this.status = "success";
        this.data = data;
    }

//    public ApiResponse(String status, String error) {
//        this.status = status;
//        this.error = error;
//    }
}


