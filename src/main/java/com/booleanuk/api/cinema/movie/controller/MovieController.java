package com.booleanuk.api.cinema.movie.controller;

import com.booleanuk.api.cinema.movie.model.Movie;
import com.booleanuk.api.cinema.movie.repository.MovieRepository;
import com.booleanuk.api.cinema.response.ResponseInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.Optional;

import static com.booleanuk.api.cinema.response.ResponseFactory.*;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    MovieRepository movieRepository;

    @PostMapping
    public ResponseEntity<ResponseInterface> addMovie(@RequestBody Movie movie) throws ResponseStatusException {
        try {
            return CreatedSuccessResponse(this.movieRepository.save(movie));
        } catch (Exception e) {
            return BadRequestErrorResponse();
        }
    }

    @GetMapping
    public ResponseEntity<ResponseInterface> getAllMovies() throws ResponseStatusException {
        return OkSuccessResponse(this.movieRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseInterface> getMovieById(@PathVariable (name = "id") int id) throws ResponseStatusException {
        if (findMovieById(id).isEmpty()){
            return NotFoundErrorResponse();
        }
        return OkSuccessResponse(findMovieById(id).get());
    }


    @PutMapping("/{id}")
    public ResponseEntity<ResponseInterface> updateMovie(@PathVariable (name = "id") int id, @RequestBody Movie updatedMovie) throws ResponseStatusException {
        if (findMovieById(id).isEmpty()) {
            return NotFoundErrorResponse();
        }

        try {
            Movie movieToUpdate = findMovieById(id).get();
            update(movieToUpdate, updatedMovie);
            return CreatedSuccessResponse(this.movieRepository.save(movieToUpdate));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "An error occurred when attempting to update movie: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseInterface> deleteMovie(@PathVariable (name = "id") int id) throws ResponseStatusException {
        if (findMovieById(id).isEmpty()){
            return NotFoundErrorResponse();
        }
        Movie movieToDelete = findMovieById(id).get();
        this.movieRepository.delete(movieToDelete);
        return OkSuccessResponse(movieToDelete);
    }

    private Optional<Movie> findMovieById(int id) {
        return this.movieRepository.findById(id);
    }

    private void update(Movie oldMovie, Movie newMovie) {
        oldMovie.setTitle(newMovie.getTitle());
        oldMovie.setRating(newMovie.getRating());
        oldMovie.setDescription(newMovie.getDescription());
        oldMovie.setRuntimeMins(newMovie.getRuntimeMins());
        oldMovie.setUpdatedAt(OffsetDateTime.now());
    }
}
