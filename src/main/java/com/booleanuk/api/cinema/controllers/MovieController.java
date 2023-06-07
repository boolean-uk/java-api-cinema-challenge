package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.Dtos.MovieDto;
import com.booleanuk.api.cinema.Dtos.ScreeningDto;
import com.booleanuk.api.cinema.entities.Movie;
import com.booleanuk.api.cinema.entities.Screening;
import com.booleanuk.api.cinema.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

record singleMovieResponse(String status, Movie data) {
}
record singleScreeningResponse(String status, Screening data) {
}
record multiMovieResponse(String status, List<Movie> data) {
}
record multiScreeningResponse(String status, List<Screening> data) {
}
@RestController
@RequestMapping("movies")
public class MovieController {
    @Autowired
    MovieService movieService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public singleMovieResponse createMovie(@RequestBody MovieDto requestMovie) {
        return new singleMovieResponse("success", movieService.createMovie(requestMovie));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public multiMovieResponse getAllMovies() {
        return new multiMovieResponse("success", movieService.getAllMovies());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public singleMovieResponse updateMovie(@PathVariable Integer id, @RequestBody Movie requestMovie) {
        return new singleMovieResponse("success", movieService.updateMovie(id, requestMovie));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public singleMovieResponse deleteMovie(@PathVariable Integer id) {
        return new singleMovieResponse("success", movieService.deleteMovie(id));
    }
    @PostMapping("/{id}/screenings")
    @ResponseStatus(HttpStatus.CREATED)
    public singleScreeningResponse createScreeningForMovie(@PathVariable Integer id, @RequestBody ScreeningDto requestScreening) {
        return new singleScreeningResponse("success", movieService.createScreeningForMovie(id,requestScreening));
    }
    @GetMapping("/{id}/screenings")
    @ResponseStatus(HttpStatus.OK)
    public multiScreeningResponse getAllScreeningForMovie(@PathVariable Integer id) {
        return new multiScreeningResponse("success", movieService.getAllScreeningForMovie(id));
    }

}
