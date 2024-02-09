package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("movies")
public class MovieController {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ScreeningRepository screeningRepository;

    @GetMapping
    public List<Movie> getAllMovies() {
        return this.movieRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        if (movie.getTitle() == null || movie.getRating() == null || movie.getDescription() == null || movie.getRuntimeMinutes() == 0) {
            return ResponseEntity.badRequest().build();
        }
        return new ResponseEntity<>(this.movieRepository.save(movie), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Movie> deleteMovieById(@PathVariable int id) {
        Movie movieToDelete = getAMovie(id);
        this.movieRepository.delete(movieToDelete);
        movieToDelete.setScreenings(new ArrayList<>());
        return ResponseEntity.ok(movieToDelete);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovieById(@PathVariable int id, @RequestBody Movie movie) {
        if (movie.getTitle() == null || movie.getRating() == null || movie.getDescription() == null || movie.getRuntimeMinutes() == 0) {
            return ResponseEntity.badRequest().build();
        }
        Movie movieToUpdate = getAMovie(id);
        movieToUpdate.setTitle(movie.getTitle());
        movieToUpdate.setRating(movie.getRating());
        movieToUpdate.setDescription(movie.getDescription());
        movieToUpdate.setRuntimeMinutes(movie.getRuntimeMinutes());
        movieToUpdate.setScreenings(movie.getScreenings());
        return new ResponseEntity<>(this.movieRepository.save(movieToUpdate), HttpStatus.CREATED);
    }

    @GetMapping("/{id}/screenings")
    public ResponseEntity<?> getAllScreeningsByMovieId(@PathVariable int id) {
        Optional<Movie> optionalMovie = movieRepository.findById(id);

        if (optionalMovie.isPresent()) {
            List<Screening> screenings = optionalMovie.get().getScreenings();
            if (screenings != null && !screenings.isEmpty()) {
                return ResponseEntity.ok(screenings);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{id}/screenings")
    public ResponseEntity<Screening> createScreening(@PathVariable int id, @RequestBody Screening screening) {
        if (screening.getMovie() == null || screening.getScreenNumber() < 1 || screening.getCapacity() < 1 || screening.getStartsAt() == null) {
            return ResponseEntity.badRequest().build();
        }
        Movie tempMovie = getAMovie(screening.getMovie().getId());
        screening.setMovie(tempMovie);
        return ResponseEntity.ok(this.screeningRepository.save(screening));
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
