package com.booleanuk.api.cinema.payload.response;

import com.booleanuk.api.cinema.model.Movie;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MovieListResponse extends Response<List<Movie>>{
    public MovieListResponse(List<Movie> data) {
        super(data);
    }
}
