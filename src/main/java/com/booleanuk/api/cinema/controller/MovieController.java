package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.ApiResponse;
import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("movies")
public class MovieController {
    @Autowired
    private final MovieRepository repository;

    @Autowired
    private final ScreeningRepository sRepository;

    public MovieController(MovieRepository repository, ScreeningRepository sRepository) {
        this.repository = repository;
        this.sRepository = sRepository;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> create(@RequestBody Movie movieDetails) {
        if (!isValidMovie(movieDetails)) {
            ApiResponse<String> response = new ApiResponse<>("error", "Could not create the specified movie, please check that all fields are correct.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Movie movie = new Movie(movieDetails.getTitle(), movieDetails.getRating(), movieDetails.getDescription(), movieDetails.getRuntimeMins());
        ApiResponse<Movie> response = new ApiResponse<>("success", repository.save(movie));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("{id}/screenings")
    public ResponseEntity<ApiResponse<?>> createScreening(@PathVariable int id, @RequestBody Screening screeningDetails) {
        if (!isValidScreening(screeningDetails)) {
            ApiResponse<String> response = new ApiResponse<>("error", "Could not create a screening for the specified movie, please check all fields are correct");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Optional<Movie> mov = this.repository.findById(id);

        if (mov.isEmpty()) {
            ApiResponse<String> response = new ApiResponse<>("error", String.format("No movie with id %d found.", id));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Movie movie = mov.get();
        Screening screening = new Screening(screeningDetails.getScreenNumber(), screeningDetails.getCapacity(), screeningDetails.getStartsAt(), movie);

        ApiResponse<Screening> response = new ApiResponse<>("success", sRepository.save(screening));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Movie>>> readAll() {
        List<Movie> movies = this.repository.findAll();
        ApiResponse<List<Movie>> response = new ApiResponse<>("success", movies);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("{id}/screenings")
    public ResponseEntity<ApiResponse<?>> readAllScreenings(@PathVariable int id) {
        Optional<Movie> mov = this.repository.findById(id);

        if (mov.isEmpty()) {
            ApiResponse<String> response = new ApiResponse<>("error", String.format("No movie with id %d found.", id));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        List<Screening> screenings = this.sRepository.findAll();
        ApiResponse<List<Screening>> response = new ApiResponse<>("success", screenings);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<ApiResponse<?>> update(@PathVariable int id, @RequestBody Movie movieDetails) {
        if (!isValidMovie(movieDetails)) {
            ApiResponse<String> response = new ApiResponse<>("error", "Could not update the specified movie, please check all fields are correct.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        Optional<Movie> mov = this.repository.findById(id);

        if (mov.isEmpty()) {
            ApiResponse<String> response = new ApiResponse<>("error", String.format("No movie with id %d found.", id));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Movie movie = mov.get();
        movie.setTitle(movieDetails.getTitle());
        movie.setDescription(movieDetails.getDescription());
        movie.setRating(movieDetails.getRating());
        movie.setRuntimeMins(movieDetails.getRuntimeMins());
        movie.setUpdatedAt(OffsetDateTime.now());

        ApiResponse<Movie> response = new ApiResponse<>("success", repository.save(movie));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<?>> delete(@PathVariable int id) {
        Optional<Movie> mov = this.repository.findById(id);

        if (mov.isEmpty()) {
            ApiResponse<String> response = new ApiResponse<>("error", String.format("No movie with id %d found.", id));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        Movie movie = mov.get();
        repository.delete(movie);

        ApiResponse<Movie> response = new ApiResponse<>("success", movie);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private boolean isValidMovie(Movie movie) {
        return !(StringUtils.isBlank(movie.getTitle())
                || StringUtils.isBlank(movie.getDescription())
                || StringUtils.isBlank(movie.getRating())
                || movie.getRuntimeMins() == null);
    }

    private boolean isValidScreening(Screening screening) {
        return !(screening.getScreenNumber() == null
                || screening.getCapacity() == null
                || screening.getStartsAt() == null);
    }
}
