package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.response.*;
import com.booleanuk.api.cinema.response.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("movies")
public class MovieController {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ScreeningRepository screeningRepository;

    private LocalDateTime time = LocalDateTime.now();

    @GetMapping
    public ResponseEntity<Response> getAllMovies() {
        List<Movie> movies = this.movieRepository.findAll();
        return new ResponseEntity<>(new MovieListResponse(this.movieRepository.findAll()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Response> createMovie(@RequestBody Movie movie) {

        if(!movie.getDescription().isEmpty() && !movie.getTitle().isEmpty() && !movie.getRating().isEmpty() && movie.getRuntimeMins() > 0) {
            movie.setCreatedAt(time);
            movie.setUpdatedAt(time);
            Movie newMovie = this.movieRepository.save(movie);
            if(movie.getScreenings() != null) {
                List<Screening> newList = new ArrayList<>();
                for(Screening screening : movie.getScreenings()) {
                    Screening s = new Screening(screening.getScreenNumber(), screening.getStartsAt(), screening.getCapacity());
                    s.setMovie(movie);
                    s.setCreatedAt(LocalDateTime.now());
                    s.setUpdatedAt(LocalDateTime.now());
                    screeningRepository.save(s);
                    newList.add(s);
                }
                movie.setScreenings(newList);
            } else {
                newMovie.setScreenings(new ArrayList<>());
            }
            return new ResponseEntity<>(new MovieResponse(newMovie), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(new ErrorResponse(new Error("bad request")), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateMovie(@PathVariable int id, @RequestBody Movie movie) {
        Movie movieToUpdate = movieRepository.findById(id)
                .orElse(null);
        if(movieToUpdate == null) {
            return new ResponseEntity<>(new ErrorResponse(new Error("not found")), HttpStatus.NOT_FOUND);
        }
        if(!movie.getDescription().isEmpty() && !movie.getTitle().isEmpty() && !movie.getRating().isEmpty() && movie.getRuntimeMins() > 0) {
            movieToUpdate.setTitle(movie.getTitle());
            movieToUpdate.setRating(movie.getRating());
            movieToUpdate.setDescription(movie.getDescription());
            movieToUpdate.setRuntimeMins(movie.getRuntimeMins());
            movieToUpdate.setUpdatedAt(LocalDateTime.now());
            return new ResponseEntity<>(new MovieResponse(this.movieRepository.save(movieToUpdate)), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(new ErrorResponse(new Error("bad request")), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteMovie(@PathVariable int id) {
        Movie movieToDelete = movieRepository.findById(id)
                .orElse(null);
        if(movieToDelete == null) {
            return new ResponseEntity<>(new ErrorResponse(new Error("not found")), HttpStatus.NOT_FOUND);
        }
        if(!movieToDelete.getScreenings().isEmpty()) {
            return new ResponseEntity<>(new ErrorResponse(new Error("Movie has screenings, and could not be deleted")), HttpStatus.BAD_REQUEST);
        }
        this.movieRepository.delete(movieToDelete);
        movieToDelete.setScreenings(new ArrayList<>());
        return new ResponseEntity<>(new MovieResponse(movieToDelete), HttpStatus.OK);
    }

    @GetMapping("/{id}/screenings")
    public ResponseEntity<Response> getAllScreenings(@PathVariable int id) {
        Movie movie = movieRepository.findById(id)
                .orElse(null);
        if(movie == null) {
            return new ResponseEntity<>(new ErrorResponse(new Error("not found")), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ScreeningListResponse(movie.getScreenings()), HttpStatus.OK);
    }

    @PostMapping("/{id}/screenings")
    public ResponseEntity<Response> createScreening(@PathVariable int id, @RequestBody Screening screening) {
        if(screening.getCapacity() > 0 && screening.getScreenNumber() > 0) {
            screening.setCreatedAt(time);
            screening.setUpdatedAt(time);
            Movie movie = movieRepository.findById(id)
                    .orElse(null);
            if(movie == null) {
                return new ResponseEntity<>(new ErrorResponse(new Error("bad request")), HttpStatus.NOT_FOUND);
            }
            screening.setMovie(movie);
            this.screeningRepository.save(screening);
            return new ResponseEntity<>(new ScreeningResponse(screening), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(new ErrorResponse(new Error("bad request")), HttpStatus.BAD_REQUEST);
        }
    }
}
