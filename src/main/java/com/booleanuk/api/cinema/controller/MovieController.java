package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.exceptions.CustomDataNotFoundException;
import com.booleanuk.api.cinema.exceptions.CustomParameterConstraintException;
import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.util.DateCreater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("movies")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        movie.setCreatedAt(DateCreater.getCurrentDate());
        movie.setUpdatedAt(DateCreater.getCurrentDate());


        areMovieValid(movie);
        return new ResponseEntity<>(this.movieRepository.save(movie), HttpStatus.CREATED);


    }

    @GetMapping
    public List<Movie> getMovies() {
        return this.movieRepository.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable (name = "id") int id, @RequestBody Movie movie) {

        Movie movieToBeUpdated = this.getAMovie(id);
        areMovieValid(movieToBeUpdated);

        movieToBeUpdated.setScreenings(movie.getScreenings());
        movieToBeUpdated.setUpdatedAt(DateCreater.getCurrentDate());
        movieToBeUpdated.setRating(movie.getRating());
        movieToBeUpdated.setDescription(movie.getDescription());
        movieToBeUpdated.setRuntimeMins(movie.getRuntimeMins());
        movieToBeUpdated.setTitle(movie.getTitle());

        return new ResponseEntity<>(this.movieRepository.save(movieToBeUpdated), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Movie> deleteMovie(@PathVariable (name = "id") int id) {
        Movie movieToBeDeleted = getAMovie(id);
        movieToBeDeleted.setScreenings(new ArrayList<>());
        this.movieRepository.delete(movieToBeDeleted);
        return new ResponseEntity<>(movieToBeDeleted, HttpStatus.OK);
    }


    private Movie getAMovie(int id) {
        return this.movieRepository.findById(id).orElseThrow(() -> new CustomDataNotFoundException("No Movie with that id were find."));
    }

    private void areMovieValid(Movie movie) {
        if(movie.getDescription() == null || movie.getRating() == null || movie.getTitle() == null || movie.getUpdatedAt() == null || movie.getCreatedAt() == null) {
            throw new CustomParameterConstraintException("Could not create a new movie, please check all fields are correct");
        }

    }


}
