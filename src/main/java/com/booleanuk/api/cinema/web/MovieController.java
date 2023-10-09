package com.booleanuk.api.cinema.web;

import com.booleanuk.api.cinema.domain.dtos.CreateMovieRequestDTO;
import com.booleanuk.api.cinema.domain.dtos.MovieResponseDTO;
import com.booleanuk.api.cinema.domain.dtos.UpdateMovieRequestDTO;
import com.booleanuk.api.cinema.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    public MovieResponseDTO createMovie(@RequestBody CreateMovieRequestDTO movieDTO) {
        return movieService.createMovie(movieDTO);
    }

    @GetMapping
    public List<MovieResponseDTO> getAllMovies() {
        return movieService.getAllMovies();
    }

    @GetMapping("/{id}")
    public MovieResponseDTO getMovieById(@PathVariable Long id) {
        return movieService.getMovieById(id);
    }

    @PutMapping("/{id}")
    public MovieResponseDTO updateMovie(@PathVariable Long id, @RequestBody UpdateMovieRequestDTO movieDTO) {
        return movieService.updateMovie(id, movieDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
    }
}