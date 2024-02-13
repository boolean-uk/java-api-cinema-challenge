package com.booleanuk.api.cinema.response;
import com.booleanuk.api.cinema.model.MovieDTO;

import java.util.List;

public class MovieDTOListResponse extends Response<List<MovieDTO>> {

    public MovieDTOListResponse(List<MovieDTO> data) {
        super("Success",data);
    }
}