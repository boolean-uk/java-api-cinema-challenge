package com.booleanuk.api.cinema.Controller;

import com.booleanuk.api.cinema.ApiResponse.ApiResponse;
import com.booleanuk.api.cinema.Model.Movie;
import com.booleanuk.api.cinema.Model.Screening;
import com.booleanuk.api.cinema.Repository.MovieRepository;
import com.booleanuk.api.cinema.Repository.ScreeningRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("movies")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ScreeningRepository screeningRepository;

    @GetMapping
    public ApiResponse<List<Movie>> getAllMovies() {
        List<Movie> movies = this.movieRepository.findAll();
        return new ApiResponse<>("success", movies);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Movie>> createMovie(@RequestBody Movie movie) {
        Movie savedMovie = this.movieRepository.save(movie);

        if (movie.getScreenings()  != null ) {
            List<Screening> screenings = new ArrayList<>(movie.getScreenings());

            for (Screening screening : screenings) {
                screening.setMovie(savedMovie);
                this.screeningRepository.save(screening);
            }
            savedMovie.setScreenings(screenings);
        }
        return new ResponseEntity<>(new ApiResponse<>("success", savedMovie), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Movie>> getMovieById(@PathVariable int id) {
        Movie movie = null;
        movie = this.movieRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No movies matching that id were found"));
        ApiResponse<Movie> response = new ApiResponse<>("success", movie);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Movie>> updateMovie(@PathVariable int id, @RequestBody Movie movie) {
        Movie movieToUpdate = this.movieRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No movies matching that id were found"));

        movieToUpdate.setTitle(movie.getTitle());
        movieToUpdate.setRating(movie.getRating());
        movieToUpdate.setDescription(movie.getDescription());
        movieToUpdate.setRuntimeMins(movie.getRuntimeMins());
        return new ResponseEntity<>(new ApiResponse<>("success", this.movieRepository.save(movieToUpdate)), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<ApiResponse<Movie>> deleteMovie(@PathVariable int id) {
        Movie movieToDelete = this.movieRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No movies matching that id were found"));

        //Saving the screenings in a list
        List<Screening> screenings = movieToDelete.getScreenings();

        //Delete the associated screenings
        for (Screening screening : screenings) {
            this.screeningRepository.delete(screening);
        }

        //Delete movie entity
        this.movieRepository.delete(movieToDelete);

        ApiResponse<Movie> response = new ApiResponse<>("success", movieToDelete);
        return ResponseEntity.ok(response);
    }
}
