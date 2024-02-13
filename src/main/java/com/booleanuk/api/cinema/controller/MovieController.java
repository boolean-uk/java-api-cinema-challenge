package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.response.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("movies")
public class MovieController {
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ScreeningRepository screeningRepository;
    @GetMapping
    public ResponseEntity<MovieListResponse> getAllMovie() {
        MovieListResponse movieListResponse = new MovieListResponse();
        movieListResponse.set(this.movieRepository.findAll());
        return ResponseEntity.ok(movieListResponse);
    }

    private Movie getAMovie(int id) {
        return this.movieRepository.findById(id).orElse(null);
    }

    @PostMapping
    public ResponseEntity<ResponseGeneric<?>> createMovie(@RequestBody Movie movie){

        if(movie.getTitle() == null || movie.getRating() == null|| movie.getDescription() == null || movie.getRuntimeMins() == null){
            ErrorResponse error = new ErrorResponse();
            error.set("bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        Movie savedMovie = this.movieRepository.save(movie);
        if(movie.getScreenings() != null){
            List<Screening> screenings = new ArrayList<>(movie.getScreenings());

            for (Screening screening : screenings){
                screening.setMovie(movie);
                this.screeningRepository.save(screening);
            }
            savedMovie.setScreenings(screenings);
        }
       // Movie savedMovie = this.movieRepository.save(theSaveMovie);
        MovieResponse movieResponse = new MovieResponse();
        movieResponse.set(savedMovie);


        return new ResponseEntity<>(movieResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseGeneric<?>> getOne(@PathVariable int id){
        Movie movie = this.movieRepository.findById(id).orElse(null);
        if(movie == null){
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        MovieResponse movieResponse = new MovieResponse();
        movieResponse.set(movie);
        return ResponseEntity.ok(movieResponse);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ResponseGeneric<?>> updateMovie(@PathVariable int id, @RequestBody Movie movie){
        Movie movieToUpdate = this.getAMovie(id);
        if(movie.getTitle() == null || movie.getRating() == null|| movie.getDescription() == null || movie.getRuntimeMins() == null){
            ErrorResponse error = new ErrorResponse();
            error.set("bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        movieToUpdate.setTitle(movie.getTitle());
        movieToUpdate.setRating(movie.getRating());
        movieToUpdate.setDescription(movie.getDescription());
        movieToUpdate.setRuntimeMins(movie.getRuntimeMins());
        //movieToUpdate.setCreatedAt(movie.getCreatedAt());
        movieToUpdate.setUpdatedAt(movie.getUpdatedAt());
        Movie updatedMovie = this.movieRepository.save(movieToUpdate);
        MovieResponse movieResponse = new MovieResponse();
        movieResponse.set(updatedMovie);


        return new ResponseEntity<>(movieResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Response<Movie>> deleteMovie (@PathVariable int id){
        Movie movieToDelete = this.getAMovie(id);

        //get the screening associated with the movie, first delete the screening then delete the movie
        List<Screening> screenings = movieToDelete.getScreenings();
        for (Screening screening : screenings){
            this.screeningRepository.delete(screening);
        }
        this.movieRepository.delete(movieToDelete);

        MovieResponse movieResponse = new MovieResponse();
        movieResponse.set(movieToDelete);
        return  ResponseEntity.ok(new Response<>("success",movieToDelete));
    }


}
