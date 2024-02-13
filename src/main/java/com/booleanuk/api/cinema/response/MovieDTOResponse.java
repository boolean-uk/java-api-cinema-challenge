package com.booleanuk.api.cinema.response;
import com.booleanuk.api.cinema.model.MovieDTO;

public class MovieDTOResponse extends Response<MovieDTO> {
    private MovieDTO data;
    public MovieDTOResponse(MovieDTO moviedto) {
        super("Success",moviedto);
    }
}