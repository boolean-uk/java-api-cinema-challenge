package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.responses.ErrorResponse;
import com.booleanuk.api.cinema.responses.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {
    private MovieRepository movieRepository;
    private ScreeningRepository screeningRepository;

    public MovieController(MovieRepository movieRepository, ScreeningRepository screeningRepository) {
        this.movieRepository = movieRepository;
        this.screeningRepository = screeningRepository;
    }

    private record MovieWithScreenings(String title, String rating, String description, int runtimeMins, List<Screening> screenings) {};

    @PostMapping
    public ResponseEntity<Response<?>> createMovie(@RequestBody MovieWithScreenings movieWithScreenings) {
        Movie movie = new Movie(movieWithScreenings.title, movieWithScreenings.rating, movieWithScreenings.description, movieWithScreenings.runtimeMins);
        Movie createdMovie = movieRepository.save(movie);
        if (createdMovie == null) {
            return new ResponseEntity<>(new ErrorResponse("bad request"), HttpStatus.BAD_REQUEST);
        }
        if (movieWithScreenings.screenings != null) {
            for (Screening s : movieWithScreenings.screenings) {
                s.setMovie(createdMovie);
                screeningRepository.save(s);
            }
        }
        return new ResponseEntity<>(new Response<>("success", createdMovie), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getOne(@PathVariable int id) {
        Movie movie = movieRepository.findById(id).orElse(null);
        if (movie == null) {
            return new ResponseEntity<>(new ErrorResponse("not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new Response<>("success", movie), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Response<?>> getAll() {
        return new ResponseEntity<>(new Response<>("success", movieRepository.findAll()), HttpStatus.OK);
    }

    @PostMapping("{id}/screenings")
    public ResponseEntity<Response<?>> createScreening(@PathVariable(name = "id") int movieId, @RequestBody Screening screening) {
        Movie movie = movieRepository.findById(movieId).orElse(null);
        if (movie == null) {
            return new ResponseEntity<>(new ErrorResponse("not found"), HttpStatus.NOT_FOUND);
        }

        screening.setMovie(movie);
        screening.setCreatedAt(LocalDateTime.now());
        screening.setUpdatedAt(LocalDateTime.now());
        Screening createdScreening = screeningRepository.save(screening);

        if (createdScreening == null) {
            return new ResponseEntity<>(new ErrorResponse("bad request"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new Response<>("success", createdScreening), HttpStatus.CREATED);
    }

    @GetMapping("/{id}/screenings")
    public ResponseEntity<Response<?>> getAllScreenings(@PathVariable(name = "id") int movieId) {
        Movie movie = movieRepository.findById(movieId).orElse(null);
        if (movie == null)
            return new ResponseEntity<>(new ErrorResponse("not found"), HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(new Response<>("success", screeningRepository.findAll().stream().filter(
                s -> s.getMovieId() == movie.getId()).toList()), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateMovie(@PathVariable int id, @RequestBody Movie movie) {
        Movie movieToUpdate = movieRepository.findById(id).orElse(null);
        if (movieToUpdate == null) {
            return new ResponseEntity<>(new ErrorResponse("not found"), HttpStatus.NOT_FOUND);
        }

        if (movie.getTitle() != null)
            movieToUpdate.setTitle(movie.getTitle());
        if (movie.getRating() != null)
            movieToUpdate.setRating(movie.getRating());
        if (movie.getDescription() != null)
            movieToUpdate.setDescription(movie.getDescription());
        if (movie.getRuntimeMins() > 0)
            movieToUpdate.setRuntimeMins(movie.getRuntimeMins());
        movieToUpdate.setUpdatedAt(LocalDateTime.now());

        if (movieRepository.save(movieToUpdate) == null) {
            return new ResponseEntity<>(new ErrorResponse("bad request"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new Response<>("success", movieToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteMovie(@PathVariable int id) {
        Movie movie = movieRepository.findById(id).orElse(null);
        if (movie == null)
            return new ResponseEntity<>(new ErrorResponse("not found"), HttpStatus.NOT_FOUND);
        movieRepository.deleteById(id);
        return ResponseEntity.ok(new Response<>("success", movie));
    }
}
