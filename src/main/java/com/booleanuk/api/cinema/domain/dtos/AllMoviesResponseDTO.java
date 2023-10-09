package com.booleanuk.api.cinema.domain.dtos;

import lombok.Data;

import java.util.List;
@Data
public class AllMoviesResponseDTO {
    private List<MovieResponseDTO> movies;

}