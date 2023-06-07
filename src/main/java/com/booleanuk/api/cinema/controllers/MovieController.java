package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.Dtos.movies.MovieRequestDto;
import com.booleanuk.api.cinema.Dtos.movies.MovieResponseDto;
import com.booleanuk.api.cinema.Dtos.movies.MovieResponseSingleDto;
import com.booleanuk.api.cinema.entities.Movie;
import com.booleanuk.api.cinema.repositories.MovieRepo;
import com.booleanuk.api.cinema.services.movie.MovieServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("movies")
public class MovieController {

    @Autowired
    MovieServiceInterface movieService;

    @GetMapping
    public MovieResponseDto getAllMovies() {
        return new MovieResponseDto("success",movieService.getAllMovie());
    }
//    @PostMapping
//    public MovieResponseSingleDto createMovie(@RequestBody Movie movie) {
//        return new MovieResponseSingleDto("success",movieService.createMovie(movie));
//    }
    @PostMapping
    public MovieResponseSingleDto createMovie(@RequestBody MovieRequestDto movieRequestDto) {
        return new MovieResponseSingleDto("success",movieService.createMovie(movieRequestDto));
    }
    @PutMapping("/{id}")
    public MovieResponseSingleDto updateMovie(@PathVariable Integer id,@RequestBody Movie movie) {
        return new MovieResponseSingleDto("success",movieService.updateMovie(id,movie));
    }
    @DeleteMapping("/{id}")
    public MovieResponseSingleDto updateMovie(@PathVariable Integer id) {
        return new MovieResponseSingleDto("success",movieService.deleteMovie(id));
    }

}
