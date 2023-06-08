package com.booleanuk.api.cinema.model.dto;

import java.util.List;

public record MoviesResponse (
        String status,
        List<MovieData> data
){
}
