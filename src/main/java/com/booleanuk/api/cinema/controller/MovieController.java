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
    public ResponseEntity<ApiResponse<Movie>> getAllMovies() {
        try {
            List<Movie> movies = this.movieRepository.findAll();
            if (movies.isEmpty()) {
                ApiResponse<Movie> badRequest = new ApiResponse<>("error", new ApiResponse.Message("bad request"));
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequest);
            } else {
                ApiResponse<Movie> okRequest = new ApiResponse<>("success", movies);
                return ResponseEntity.ok(okRequest);
            }
        } catch (Exception e) {
            return null;
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
    public ResponseEntity<ApiResponse<Movie>> createMovie(@RequestBody Movie movie) {
        try {
            Date date = new Date();
            movie.setCreatedAt(date);
            movie.setUpdatedAt(date);
            if (HelperUtils.invalidMovieFields(movie)) {
                ApiResponse<Movie> badRequest = new ApiResponse<>("error", new ApiResponse.Message("bad request"));
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequest);
            } else {
                Movie savedMovie = this.movieRepository.save(movie);
                ApiResponse<Movie> createdRequest = new ApiResponse<>("success", savedMovie);
                return ResponseEntity.status(HttpStatus.CREATED).body(createdRequest);
            }
        } catch (Exception e) {
            ApiResponse<Movie> badRequest = new ApiResponse<>("error", new ApiResponse.Message("bad request caused exception thrown"));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequest);
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
    public ResponseEntity<ApiResponse<Movie>> deleteMovieById(@PathVariable int id) {
        try {
            Movie movieToDelete = getAMovie(id);
            if (movieToDelete == null || !movieToDelete.getScreenings().isEmpty()) {
                ApiResponse<Movie> badRequest = new ApiResponse<>("error", new ApiResponse.Message("bad request"));
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequest);
            } else {
                this.movieRepository.delete(movieToDelete);
                movieToDelete.setScreenings(new ArrayList<>());
                ApiResponse<Movie> okRequest = new ApiResponse<>("success", movieToDelete);
                return ResponseEntity.status(HttpStatus.OK).body(okRequest);
            }
        } catch (Exception e) {
            ApiResponse<Movie> badRequest = new ApiResponse<>("error", new ApiResponse.Message("bad request"));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequest);
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
    public ResponseEntity<ApiResponse<Movie>> updateMovieById(@PathVariable int id, @RequestBody Movie movie) {
        try {
            Movie movieToUpdate = getAMovie(id);
            if (HelperUtils.invalidMovieFields(movie)) {
                ApiResponse<Movie> badRequest = new ApiResponse<>("error", new ApiResponse.Message("bad request"));
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequest);
            } else {
                movieToUpdate.setTitle(movie.getTitle());
                movieToUpdate.setRating(movie.getRating());
                movieToUpdate.setDescription(movie.getDescription());
                movieToUpdate.setRuntimeMinutes(movie.getRuntimeMinutes());
                movieToUpdate.setScreenings(movie.getScreenings());
                this.movieRepository.save(movieToUpdate);
                ApiResponse<Movie> createdRequest = new ApiResponse<>("success", movieToUpdate);
                return ResponseEntity.status(HttpStatus.CREATED).body(createdRequest);
            }
        } catch (Exception e) {
            ApiResponse<Movie> badRequest = new ApiResponse<>("error", new ApiResponse.Message("bad request"));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequest);
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
                ApiResponse<?> okRequest = new ApiResponse<>("success", screenings);
                return ResponseEntity.status(HttpStatus.OK).body(okRequest);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>("error", new ApiResponse.Message("This movie has no screenings yet!")));
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse<>("error", new ApiResponse.Message("No movie with such ID!")));
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
    public ResponseEntity<ApiResponse<Screening>> createScreening(@PathVariable int id, @RequestBody Screening screening) {
        Date date = new Date();
        screening.setCreatedAt(date);
        screening.setUpdatedAt(date);
        if (HelperUtils.invalidScreeningFields(screening)) {
            ApiResponse<Screening> badRequest = new ApiResponse<>("error", new ApiResponse.Message("bad request"));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequest);
        } else {
            Optional<Movie> optionalMovie = movieRepository.findById(id);
            optionalMovie.ifPresent(screening::setMovie);
            Screening savedScreening = this.screeningRepository.save(screening);
            ApiResponse<Screening> createdRequest = new ApiResponse<>("success", savedScreening);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdRequest);
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
