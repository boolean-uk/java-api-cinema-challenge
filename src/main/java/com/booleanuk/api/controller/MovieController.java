package com.booleanuk.api.controller;

import com.booleanuk.api.model.DataStatus;
import com.booleanuk.api.model.Movie;
import com.booleanuk.api.model.Screening;
import com.booleanuk.api.repository.MovieRepository;
import com.booleanuk.api.repository.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private ScreeningRepository screeningRepository;
    @Autowired
    private MovieRepository movieRepository;

    @GetMapping
    public List<Movie> getAll() {
        return movieRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Movie> create(@RequestBody Movie movie) {
        Movie savedMovie = movieRepository.save(movie);
        return new ResponseEntity<>(savedMovie, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getById(@PathVariable int id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));
        return ResponseEntity.ok(movie);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Movie> delete(@PathVariable int id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));
        movieRepository.delete(movie);
        return ResponseEntity.ok(movie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> update(@PathVariable int id, @RequestBody Movie movieDetails) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));
        movie.setTitle(movieDetails.getTitle());
        movie.setRating(movieDetails.getRating());
        movie.setDescription(movieDetails.getDescription());
        movie.setRuntimeMins(movieDetails.getRuntimeMins());
        Movie updatedMovie = movieRepository.save(movie);
        return ResponseEntity.ok(updatedMovie);
    }

    @GetMapping("/{id}/screenings")
    public ResponseEntity<DataStatus> getScreeningsByMovieId(@PathVariable int id) {
        try{
            Movie movie = movieRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));
            List<Map<String, Object>> screeningsList = movie.getScreenings().stream().map(screening -> {
                Map<String, Object> screeningMap = new HashMap<>();
                screeningMap.put("id", screening.getId());
                screeningMap.put("screenNumber", screening.getScreenNumber());
                screeningMap.put("capacity", screening.getCapacity());
                screeningMap.put("startsAt", screening.getStartsAt());
                screeningMap.put("createdAt", screening.getCreatedAt());
                screeningMap.put("updatedAt", screening.getUpdatedAt());
                return screeningMap;
            }).collect(Collectors.toList());

            DataStatus response = new DataStatus("success", screeningsList);
            return ResponseEntity.ok(response);
        }catch (ResponseStatusException e) {
            DataStatus errorResponse = new DataStatus("error", Map.of("message", e.getReason()));
            return new ResponseEntity<>(errorResponse, e.getStatusCode());
        }

    }

    @PostMapping("/{id}/screenings")
    public ResponseEntity<DataStatus> addScreeningToMovie(@PathVariable int id, @RequestBody Screening screening) {
        try {
            Movie movie = movieRepository.findById(id).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found"));

            screening.setMovie(movie);
            LocalDateTime now = LocalDateTime.now();
            screening.setCreatedAt(now);
            screening.setUpdatedAt(now);
            Screening createdScreening = screeningRepository.save(screening);

            Map<String, Object> screeningData = new HashMap<>();
            screeningData.put("id", createdScreening.getId());
            screeningData.put("screenNumber", createdScreening.getScreenNumber());
            screeningData.put("capacity", createdScreening.getCapacity());
            screeningData.put("startsAt", createdScreening.getStartsAt());
            screeningData.put("createdAt", createdScreening.getCreatedAt());
            screeningData.put("updatedAt", createdScreening.getUpdatedAt());

            DataStatus successResponse = new DataStatus("success", screeningData);
            return ResponseEntity.ok(successResponse);
        } catch (ResponseStatusException e) {
            DataStatus errorResponse = new DataStatus("error", Map.of("message", e.getReason()));
            return new ResponseEntity<>(errorResponse, e.getStatusCode());
        } catch (Exception e) {
            // Handle other exceptions, such as database errors
            DataStatus errorResponse = new DataStatus("error", Map.of("message", "Internal server error"));
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
