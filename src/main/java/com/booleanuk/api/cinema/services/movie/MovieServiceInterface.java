package com.booleanuk.api.cinema.services.movie;

import com.booleanuk.api.cinema.Dtos.movies.MovieRequestDto;
import com.booleanuk.api.cinema.Dtos.screenings.ScreeningDto;
import com.booleanuk.api.cinema.Dtos.screenings.ScreeningNew;
import com.booleanuk.api.cinema.entities.Movie;
import com.booleanuk.api.cinema.entities.Screening;

import java.util.List;

public interface MovieServiceInterface {
    Movie createMovie(MovieRequestDto movie);
    List<Movie> getAllMovie();

    Movie updateMovie(int id,Movie movie);

    Movie deleteMovie(int id);
    List<ScreeningDto> getScreeningsForMovieId(int id);

    ScreeningDto createScreeningForMovieId(int id, ScreeningNew screeningNew);

}
