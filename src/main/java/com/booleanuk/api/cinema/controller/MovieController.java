package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.utility.DataResponse;
import com.booleanuk.api.cinema.utility.ErrorResponse;
import com.booleanuk.api.cinema.utility.MovieDTO;
import com.booleanuk.api.cinema.utility.Responses.MovieListResponse;
import com.booleanuk.api.cinema.utility.Responses.MovieResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {
    @Autowired
    private MovieRepository movies;

    @PostMapping
    public ResponseEntity<DataResponse<?>> createMovie(@RequestBody MovieDTO movieData) {
        Movie movieToCreate;
        try {
            Movie movie = new Movie();
            movie.setTitle(movieData.title());
            movie.setRating(movieData.rating());
            movie.setDescription(movieData.description());
            movie.setRuntimeMins(movieData.runtimeMins());
            movie.setCreatedAt(ZonedDateTime.now());
            movie.setUpdatedAt(ZonedDateTime.now());
            movieToCreate = this.movies.save(movie);
            List<Screening> screenings = movieData.screenings();
            if (screenings != null){
                String uri = "http://localhost:4000/movies/"+movieToCreate.getMovieId()+"/screenings";
                RestTemplate restTemplate = new RestTemplate();
                for (Screening screening : screenings){
                    restTemplate.postForEntity(uri, screening, String.class);
                }
            }
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Could not create a new movie, please check all fields are correct.");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        MovieResponse response = new MovieResponse();
        response.set(movieToCreate);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<MovieListResponse> getAllMovies() {
        MovieListResponse response = new MovieListResponse();
        response.set(this.movies.findAll(Sort.by(Sort.Direction.ASC,"movieId")));
        return ResponseEntity.ok(response);
    }

    private Movie getMovie(int id) {
        return this.movies.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DataResponse<?>> updateMovie(@PathVariable int id, @RequestBody Movie movie) {
        Movie movieToUpdate = this.getMovie(id);
        if (movieToUpdate == null){
            ErrorResponse error = new ErrorResponse();
            error.set("No movie with that id found.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        try {
            movieToUpdate.setTitle(movie.getTitle());
            movieToUpdate.setRating(movie.getRating());
            movieToUpdate.setDescription(movie.getDescription());
            movieToUpdate.setRuntimeMins(movie.getRuntimeMins());
            movieToUpdate.setUpdatedAt(ZonedDateTime.now());
            movieToUpdate = this.movies.save(movieToUpdate);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("Could not update the specified movie, please check all fields are correct.");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        MovieResponse response = new MovieResponse();
        response.set(movieToUpdate);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DataResponse<?>> deleteMovie(@PathVariable int id) {
        Movie movieToDelete = this.getMovie(id);
        if (movieToDelete == null) {
            ErrorResponse error = new ErrorResponse();
            error.set("No movie with that id found.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        List<Screening> screenings = movieToDelete.getScreenings();
        for (Screening screening: screenings){
            screening.setMovie(null);
        }
        this.movies.delete(movieToDelete);
        MovieResponse response = new MovieResponse();
        response.set(movieToDelete);
        return ResponseEntity.ok(response);
    }
}
