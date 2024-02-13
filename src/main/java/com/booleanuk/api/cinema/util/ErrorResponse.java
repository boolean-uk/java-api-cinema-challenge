package com.booleanuk.api.cinema.util;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


//https://auth0.com/blog/get-started-with-custom-error-handling-in-spring-boot-java/

@Getter
@Setter
public class ErrorResponse {

    private String status;


    private Map<String, Object> data;



    public ErrorResponse(
            String status,
            String message
    ) {
        this.status = status;
        this.data = new HashMap<>();
        this.data.put("message", message);
    }





}

