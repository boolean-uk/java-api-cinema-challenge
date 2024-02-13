package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.HelperUtils;
import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("movies")
public class MovieController {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ScreeningRepository screeningRepository;

    /**
     * Logic: Use ApiResponse Class (and nested Message Class) to construct a JSON object,
     * that references a Generic Type 'T', which in this case is any of the Models.
     * The method checks if any instances of Movie exists on the server.
     * If false, instantiate an ApiResponse and wrap it around the body of a Message instance.
     * If true, instantiate an ApiResponse and wrap it around the body of a List of Movies.
     * @return
     */
    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAllMovies() {
        try {
            List<Movie> movies = this.movieRepository.findAll();
            if (movies.isEmpty()) {
                return HelperUtils.badRequest(new ApiResponse.Message("bad request"));
            } else {
                return HelperUtils.okRequest(movies);
            }
        } catch (Exception e) {
            return HelperUtils.badRequest(new ApiResponse.Message("bad request caused by exception!"));
        }
    }

    /**
     * Logic: Capture a snapshot of the instantiation time of Movie (since accurate precision of creation
     * is irrelevant, doing it at the top of the method body is ok). The method checks if any of the
     * payload criteria (i.e. malformed/illogical member variables) are missing.
     * If true, instantiate an ApiResponse and wrap it around the body of a Message instance.
     * If false, instantiate an ApiResponse and wrap it around the body of the single instance of Movie.
     * @param movie
     * @return
     */
    @PostMapping
    public ResponseEntity<ApiResponse<?>> createMovie(@RequestBody Movie movie) {
        try {
            if (HelperUtils.invalidMovieFields(movie)) {
                return HelperUtils.badRequest(new ApiResponse.Message("bad request"));
            } else {
                Movie savedMovie = this.movieRepository.save(movie);
                return HelperUtils.createdRequest(savedMovie);
            }
        } catch (Exception e) {
            return HelperUtils.badRequest(new ApiResponse.Message("bad request caused by exception!"));
        }
    }

    /**
     * Logic: Fetch the instance to delete by using local helper method, and verify the fetched value
     * is not null AND does not hold any screenings.
     * If true, instantiate an ApiResponse and wrap it around the body of a Message instance.
     * If false, instantiate an ApiResponse and wrap it around the body of the single instance of Movie.
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> deleteMovieById(@PathVariable int id) {
        try {
            Movie movieToDelete = getAMovie(id);
            if (movieToDelete == null || !movieToDelete.getScreenings().isEmpty()) {
                return HelperUtils.badRequest(new ApiResponse.Message("bad request"));
            } else {
                this.movieRepository.delete(movieToDelete);
                movieToDelete.setScreenings(new ArrayList<>());
                return HelperUtils.okRequest(movieToDelete);
            }
        } catch (Exception e) {
            return HelperUtils.badRequest(new ApiResponse.Message("bad request caused by exception!"));
        }
    }

    /**
     * Logic: Fetch the instance of Movie to update using the Pathvariable. Use static helper method
     * to verify if any of the payload criteria (i.e. malformed/illogical member variables) are missing.
     * Conduct the update if payload is correct.
     * @param id
     * @param movie
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> updateMovieById(@PathVariable int id, @RequestBody Movie movie) {
        try {
            Movie movieToUpdate = getAMovie(id);
            if (HelperUtils.invalidMovieFields(movie)) {
                return HelperUtils.badRequest(new ApiResponse.Message("bad request"));
            } else {
                movieToUpdate.setTitle(movie.getTitle());
                movieToUpdate.setRating(movie.getRating());
                movieToUpdate.setDescription(movie.getDescription());
                movieToUpdate.setRuntimeMinutes(movie.getRuntimeMinutes());
                movieToUpdate.setScreenings(movie.getScreenings());
                this.movieRepository.save(movieToUpdate);
                return HelperUtils.createdRequest(movieToUpdate);
            }
        } catch (Exception e) {
            return HelperUtils.badRequest(new ApiResponse.Message("bad request caused by exception"));
        }
    }

    /**
     * This should go to ScreeningController Class, but Mapping Movies is occupied
     * by this Controller. Have to fix this. Same for method two below.
     * @param id
     * @return
     */
    @GetMapping("/{id}/screenings")
    public ResponseEntity<ApiResponse<?>> getAllScreeningsByMovieId(@PathVariable int id) {
        Optional<Movie> optionalMovie = movieRepository.findById(id);
        if (optionalMovie.isPresent()) {
            List<Screening> screenings = optionalMovie.get().getScreenings();
            if (screenings != null && !screenings.isEmpty()) {
                return HelperUtils.okRequest(screenings);
            } else {
                return HelperUtils.badRequest(new ApiResponse.Message("This movie has no scheduled screenings yet!"));
            }
        } else {
            return HelperUtils.badRequest(new ApiResponse.Message("No movie with such ID!"));
        }
    }

    /**
     * Logic: Capture a snapshot of the instantiation time of Screening (since accurate precision of creation
     * is irrelevant, doing it at the top of the method body is ok). The method checks if any of the
     * payload criteria (i.e. malformed/illogical member variables) are missing.
     * If true, instantiate an ApiResponse and wrap it around the body of a Message instance.
     * If false, instantiate an ApiResponse and wrap it around the body of the single instance of Screening.
     * @param screening
     * @return
     */
    @PostMapping("/{id}/screenings")
    public ResponseEntity<ApiResponse<?>> createScreening(@PathVariable int id, @RequestBody Screening screening) {
        if (HelperUtils.invalidScreeningFields(screening)) {
            return HelperUtils.badRequest(new ApiResponse.Message("bad request"));
        } else {
            Optional<Movie> optionalMovie = movieRepository.findById(id);
            optionalMovie.ifPresent(screening::setMovie);
            Screening savedScreening = this.screeningRepository.save(screening);
            return HelperUtils.createdRequest(savedScreening);
        }
    }

    /**
     * Helper method
     * @param id
     * @return
     */
    private Movie getAMovie(int id) {
        return this.movieRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
    }
}
