package com.booleanuk.api.cinema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenericResponse<T> {
    private String status;
    private T data;

    public GenericResponse<T> from(T cinemaEntity){
        return new GenericResponse<>(
                WrapperUtils.STATUS.SUCCESS.status,
                cinemaEntity
        );
    }

}
