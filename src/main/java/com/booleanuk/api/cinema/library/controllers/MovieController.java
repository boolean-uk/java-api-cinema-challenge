package com.booleanuk.api.cinema.library.controllers;

import com.booleanuk.api.cinema.library.models.Movie;
import com.booleanuk.api.cinema.library.payload.request.MovieRequest;
import com.booleanuk.api.cinema.library.payload.response.MovieResponse;
import com.booleanuk.api.cinema.library.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping
    public List<MovieResponse> getAllMovies() {
        return movieRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> getMovieById(@PathVariable int id) {
        return movieRepository.findById(id)
                .map(movie -> ResponseEntity.ok(toResponse(movie)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<MovieResponse> createMovie(@RequestBody MovieRequest request) {
        Movie movie = new Movie();
        movie.setTitle(request.getTitle());
        movie.setRating(request.getRating());
        movie.setDescription(request.getDescription());
        movie.setRuntimeMins(request.getRuntimeMins());

        Movie saved = movieRepository.save(movie);
        return ResponseEntity.status(201).body(toResponse(saved));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieResponse> updateMovie(@PathVariable int id, @RequestBody MovieRequest request) {
        return movieRepository.findById(id).map(movie -> {
            movie.setTitle(request.getTitle());
            movie.setRating(request.getRating());
            movie.setDescription(request.getDescription());
            movie.setRuntimeMins(request.getRuntimeMins());

            Movie updated = movieRepository.save(movie);
            return ResponseEntity.ok(toResponse(updated));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable int id) {
        if (!movieRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        movieRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private MovieResponse toResponse(Movie movie) {
        return MovieResponse.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .rating(movie.getRating())
                .description(movie.getDescription())
                .runtimeMins(movie.getRuntimeMins())
                .createdAt(movie.getCreatedAt())
                .updatedAt(movie.getUpdatedAt())
                .build();
    }
}
