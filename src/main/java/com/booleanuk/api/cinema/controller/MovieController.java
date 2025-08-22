package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.responses.ErrorResponse;
import com.booleanuk.api.cinema.responses.MovieListResponse;
import com.booleanuk.api.cinema.responses.MovieResponse;
import com.booleanuk.api.cinema.responses.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("movies")
public class MovieController {
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ScreeningRepository screeningRepository;


    @GetMapping
    public ResponseEntity<MovieListResponse> getAllMovies(){
        MovieListResponse movieListResponse = new MovieListResponse();
        movieListResponse.set(this.movieRepository.findAll());
        return ResponseEntity.ok(movieListResponse);
    }



    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<Response<?>> create(@RequestBody Movie request) {
        MovieResponse movieResponse = new MovieResponse();

        try{
            movieResponse.set(this.movieRepository.save(request));
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        if(request.getScreenings() != null){
            for(Screening screening : request.getScreenings()){
                Screening newScreening = new Screening(screening.getScreenNumber(), screening.getCapacity(), screening.getStartsAt(), request);
                this.screeningRepository.save(newScreening);
            }
        }

        return new ResponseEntity<>(movieResponse, HttpStatus.CREATED);
    }



    @PutMapping("{id}")
    public ResponseEntity<Response<?>> updateMovie(@PathVariable int id, @RequestBody Movie movie) {
        Movie movieToUpdate = null;

        try {
            movieToUpdate = this.movieRepository.findById(id).orElse(null);
            if(movieToUpdate == null){
                ErrorResponse error = new ErrorResponse();
                error.set("not found");
                return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
            }
            movieToUpdate.setTitle(movie.getTitle());
            movieToUpdate.setRating(movie.getRating());
            movieToUpdate.setDescription(movie.getDescription());
            movieToUpdate.setRuntimeMins(movie.getRuntimeMins());
            movieToUpdate.setUpdatedAt(LocalDate.now());
            movieToUpdate = this.movieRepository.save(movieToUpdate);

        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse();
            error.set("bad request");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
        MovieResponse movieResponse = new MovieResponse();
        movieResponse.set(movieToUpdate);

        return new ResponseEntity<>(movieResponse, HttpStatus.CREATED);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Response<?>> deleteMovie(@PathVariable int id) {
        Movie movieToDelete = this.movieRepository.findById(id).orElse(null);

        if(movieToDelete == null){
            ErrorResponse error = new ErrorResponse();
            error.set("not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        this.movieRepository.delete(movieToDelete);
        MovieResponse movieResponse = new MovieResponse();
        movieResponse.set(movieToDelete);

        return ResponseEntity.ok(movieResponse);
    }
}
