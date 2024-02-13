package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.exceptions.CustomDataNotFoundException;
import com.booleanuk.api.cinema.exceptions.CustomParameterConstraintException;
import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.util.DateCreater;
import com.booleanuk.api.cinema.util.SuccessResponse;
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

    @Autowired
    private ScreeningRepository screeningRepository;

    @PostMapping
    public ResponseEntity<SuccessResponse<?>> createMovie(@RequestBody Movie movie) {

        Movie postMovie = new Movie(movie.getTitle(), movie.getRating(), movie.getDescription(), movie.getRuntimeMins(), movie.getCreatedAt(), movie.getUpdatedAt());
        postMovie.setCreatedAt(DateCreater.getCurrentDate());
        postMovie.setUpdatedAt(DateCreater.getCurrentDate());


        areMovieValid(postMovie);
        this.movieRepository.save(postMovie);
        postMovie.setScreenings(new ArrayList<>());
        if (movie.getScreenings() != null) {
            for (Screening screening : movie.getScreenings()) {
                if (screening.getTickets() == null) {
                    screening.setTickets(new ArrayList<>());
                }

                Screening newScreening = new Screening(screening.getScreenNumber(), screening.getStartsAt(), screening.getCapacity(), DateCreater.getCurrentDate(), DateCreater.getCurrentDate(), postMovie);
                this.screeningRepository.save(newScreening);
                postMovie.getScreenings().add(newScreening);


            }
        }



        SuccessResponse<Movie> successResponse = new SuccessResponse<>(postMovie);

        return new ResponseEntity<>(successResponse, HttpStatus.CREATED);

    }

    @GetMapping
    public ResponseEntity<SuccessResponse<?>> getMovies() {

        List<Movie> movies = this.movieRepository.findAll();
        SuccessResponse<List<Movie>> successResponse = new SuccessResponse<>(movies);
        return ResponseEntity.ok(successResponse);

    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse<?>> updateMovie(@PathVariable (name = "id") int id, @RequestBody Movie movie) {

        Movie movieToBeUpdated = this.getAMovie(id);
        areMovieValid(movieToBeUpdated);

        movieToBeUpdated.setScreenings(movie.getScreenings());
        movieToBeUpdated.setUpdatedAt(DateCreater.getCurrentDate());
        movieToBeUpdated.setRating(movie.getRating());
        movieToBeUpdated.setDescription(movie.getDescription());
        movieToBeUpdated.setRuntimeMins(movie.getRuntimeMins());
        movieToBeUpdated.setTitle(movie.getTitle());

        SuccessResponse<Movie> successResponse = new SuccessResponse<>(movieToBeUpdated);

        return new ResponseEntity<>(successResponse, HttpStatus.CREATED);    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse<?>> deleteMovie(@PathVariable (name = "id") int id) {
        Movie movieToBeDeleted = getAMovie(id);
        movieToBeDeleted.setScreenings(new ArrayList<>());
        this.movieRepository.delete(movieToBeDeleted);
        SuccessResponse<Movie> successResponse = new SuccessResponse<>(movieToBeDeleted);

        return ResponseEntity.ok(successResponse);
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
