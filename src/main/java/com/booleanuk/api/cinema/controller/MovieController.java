
package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.ApiResponse;
import com.booleanuk.api.cinema.dto.MovieDTO;
import com.booleanuk.api.cinema.exception.NotFoundException;
import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.repository.MovieRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("movies")
public class MovieController {
    private static final String SUCCESS = "success";
    private final MovieRepository movieRepository;

    @Autowired
    public MovieController(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Movie> getAll() {
        return this.movieRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<MovieDTO> create(@Valid @RequestBody Movie body) {
        Movie movie = this.movieRepository.save(body);
        MovieDTO movieDTO = new MovieDTO(movie);
        return new ApiResponse<>(SUCCESS, movieDTO);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<MovieDTO> update(@PathVariable int id, @Valid @RequestBody Movie body) {
        return this.movieRepository.findById(id)
                .map(movie -> {
                    movie.setTitle(body.getTitle());
                    movie.setRating(body.getRating());
                    movie.setDescription(body.getDescription());
                    movie.setRuntimeMin(body.getRuntimeMin());

                    this.movieRepository.save(movie);
                    MovieDTO movieDTO = new MovieDTO(movie);
                    return new ApiResponse<>(SUCCESS, movieDTO);

                })
                .orElseThrow(() -> new NotFoundException("not found"));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<MovieDTO> delete(@PathVariable int id) {
        return this.movieRepository.findById(id)
                .map(movie -> {
                    this.movieRepository.delete(movie);
                    MovieDTO movieDTO = new MovieDTO(movie);
                    return new ApiResponse<>(SUCCESS, movieDTO);
                })
                .orElseThrow(() -> new NotFoundException("not found"));
    }
}
