package com.booleanuk.api.cinema;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenericResponse<T> {
    private static String SUCCESS_STATUS = "success";
    private String status;
    private T data;
    @JsonIgnore
    private Class<T> type;

    public GenericResponse(Class<T> type){
        this.type = type;
    }

    public GenericResponse(String status, T data){
        this.status = status;
        this.data = data;
    }

    public GenericResponse<T> from(T data){
        return new GenericResponse<>(
                SUCCESS_STATUS,
                data
        );
    }

}
