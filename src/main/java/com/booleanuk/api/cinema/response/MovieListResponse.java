package com.booleanuk.api.cinema.response;

import com.booleanuk.api.cinema.model.MovieDTO;
import java.util.List;

public class MovieListResponse extends Response<List<MovieDTO>> {
    private List<MovieDTO> data;
    public MovieListResponse(List<MovieDTO> data) {
        super("success", data);
    }
}

