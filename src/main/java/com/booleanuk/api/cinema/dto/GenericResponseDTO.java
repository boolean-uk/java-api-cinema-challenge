package com.booleanuk.api.cinema.dto;

import com.booleanuk.api.cinema.WrapperUtils;
import com.booleanuk.api.cinema.model.CinemaEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenericResponseDTO<T> {
    private String status;
    private T data;

    public GenericResponseDTO<T> from(T cinemaEntity){
        return new GenericResponseDTO<>(
                WrapperUtils.STATUS.SUCCESS.status,
                cinemaEntity
        );
    }

}
