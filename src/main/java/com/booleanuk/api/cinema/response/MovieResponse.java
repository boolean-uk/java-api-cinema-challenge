package com.booleanuk.api.cinema.response;

import com.booleanuk.api.cinema.model.Movie;
import com.fasterxml.jackson.annotation.JsonInclude;

public class MovieResponse extends Response {
    public MovieResponse(Movie data, String status) {
        super(data, status);
    }

}
