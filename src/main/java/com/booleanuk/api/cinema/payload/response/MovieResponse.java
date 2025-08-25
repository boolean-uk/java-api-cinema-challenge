package com.booleanuk.api.cinema.payload.response;

import com.booleanuk.api.cinema.model.Movie;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MovieResponse extends Response<Movie>{
    public MovieResponse(Movie data) {
        super(data);
    }
}
