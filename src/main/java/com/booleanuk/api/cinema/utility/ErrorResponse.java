package com.booleanuk.api.cinema.utility;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ErrorResponse extends DataResponse<String>{
    @Override
    public void set(String message){
        this.status = "error";
        this.data = message;
    }
}