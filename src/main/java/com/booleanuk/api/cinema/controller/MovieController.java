package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.response.*;
import com.booleanuk.api.cinema.response.Error;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @GetMapping
    public ResponseEntity<Response> getAllMovies(){
        return new ResponseEntity<>(new MovieListResponse(this.movieRepository.findAllWithoutScreenings()), HttpStatus.OK);
    }
    @GetMapping("{id}/screenings")
    public ResponseEntity<Response> getAllScreenings(@PathVariable(name="id") Integer id) {
        Movie movie = this.movieRepository.findById(id).orElse(null);
        if (movie ==null){
            return new ResponseEntity<>(new ErrorResponse(new Error("Movie not found")),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ScreeningListResponse(movie.getScreenings()), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Response> getAMovie(@PathVariable(name="id") Integer id) {
        Movie movie = this.movieRepository.findById(id).orElse(null);
        if (movie ==null){
            return new ResponseEntity<>(new ErrorResponse(new Error("Movie not found")),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new MovieResponse(movie), HttpStatus.OK);
    }

    //Post a movie
    @PostMapping
    public ResponseEntity<Response> create(@RequestBody Movie movie) {
        //Check the fields
        if(movie.getTitle().isEmpty() || movie.getRating().isEmpty() || movie.getDescription().isEmpty() || movie.getRuntimeMins() <= 0){
            return new ResponseEntity<>(new ErrorResponse(new Error("Bad request")),HttpStatus.BAD_REQUEST);
        }

        System.out.println(movie.getScreenings().get(0).toString());
        //Dates
        LocalDateTime currentDateTime = LocalDateTime.now();
        movie.setCreatedAt(currentDateTime);
        movie.setUpdatedAt(null);
        Movie createdMovie = this.movieRepository.save(movie);

        if(movie.getScreenings() != null){
            List<Screening> newList = new ArrayList<>();
            for (Screening screeningToCreate : movie.getScreenings()) {
                Screening screening = new Screening(screeningToCreate.getScreenNumber(), screeningToCreate.getStartsAt(), screeningToCreate.getCapacity());
                screening.setMovie(movie);
                LocalDateTime currentDateTime2 = LocalDateTime.now();
                screening.setCreatedAt(currentDateTime2);
                this.screeningRepository.save(screening);
                newList.add(screening);

            }
            movie.setScreenings(newList);
        }
        return new ResponseEntity<>(new MovieResponse(createdMovie), HttpStatus.CREATED);
    }

    //Post a screening
    @PostMapping("{id}/screenings")
    public ResponseEntity<Response> createScreening(@PathVariable(name="id") Integer id, @RequestBody Screening screening) {
        Movie movie = this.movieRepository.findById(id).orElse(null);
        if (movie ==null){
            return new ResponseEntity<>(new ErrorResponse(new Error("Movie not found")),HttpStatus.NOT_FOUND);
        }

        //Check the fields
        if(screening.getStartsAt() == null || screening.getScreenNumber() <= 0 || screening.getCapacity() <= 0){
            return new ResponseEntity<>(new ErrorResponse(new Error("Bad request")),HttpStatus.BAD_REQUEST);
        }

        screening.setMovie(movie);
        //Dates
        LocalDateTime currentDateTime = LocalDateTime.now();
        screening.setCreatedAt(currentDateTime);
        screening.setUpdatedAt(null);
        this.screeningRepository.save(screening);
        return new ResponseEntity<>(new ScreeningResponse(screening), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateAMovie(@PathVariable int id,@RequestBody Movie movie){
        Movie movieToUpdate = this.movieRepository.findById(id).orElse(null);
        if (movieToUpdate ==null){
            return new ResponseEntity<>(new ErrorResponse(new Error("Movie not found")),HttpStatus.NOT_FOUND);
        }
        //Check the fields
        if(movie.getTitle().isEmpty() || movie.getRating().isEmpty() || movie.getDescription().isEmpty()  || movie.getRuntimeMins() <= 0){
            return new ResponseEntity<>(new ErrorResponse(new Error("Bad request")),HttpStatus.BAD_REQUEST);
        }

        movieToUpdate.setTitle(movie.getTitle());
        movieToUpdate.setRating(movie.getRating());
        movieToUpdate.setDescription(movie.getDescription());
        movieToUpdate.setRuntimeMins(movie.getRuntimeMins());
        //Set update to right now
        LocalDateTime currentDateTime = LocalDateTime.now();
        movieToUpdate.setUpdatedAt(currentDateTime);
        this.movieRepository.save(movieToUpdate);
        return new ResponseEntity<>(new MovieResponse(movieToUpdate), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteAnMovie(@PathVariable int id){
        Movie movieToDelete = this.movieRepository.findById(id).orElse(null);
        if (movieToDelete ==null){
            return new ResponseEntity<>(new ErrorResponse(new Error("Movie not found")),HttpStatus.NOT_FOUND);
        }
        this.movieRepository.delete(movieToDelete);
        return new ResponseEntity<>(new MovieResponse(movieToDelete), HttpStatus.OK);
    }
}