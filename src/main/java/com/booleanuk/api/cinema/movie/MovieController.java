package com.booleanuk.api.cinema.movie;

import com.booleanuk.api.cinema.screening.Screening;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {
    @Autowired
    private MovieRepository movieRepository;

    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {
        validateMovieOrThrowException(movie);

        Movie newMovie = this.movieRepository.save(movie);

        if(movie.getScreenings() == null) {
            newMovie.setScreenings(new ArrayList<Screening>());
        }
        else {
            newMovie.setScreenings(movie.getScreenings());
        }

        return new ResponseEntity<>(newMovie, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Movie>> getAllMovies() {
        return ResponseEntity.ok(this.movieRepository.findAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable int id, @RequestBody Movie movie) {
        //validateMovieOrThrowException(movie);

        if(movie.getTitle() == null && movie.getRating() == null && movie.getDescription() == null && movie.getRunTimeMins() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update the specified movie, please check all fields are correct.");
        }

        Movie movieToUpdate = findMovieOrThrowException(id);

        movieToUpdate.setTitle(movie.getTitle());
        movieToUpdate.setRating(movie.getRating());
        movieToUpdate.setDescription(movie.getDescription());
        movieToUpdate.setRunTimeMins(movie.getRunTimeMins());

        this.movieRepository.save(movieToUpdate);

        return new ResponseEntity<>(movieToUpdate, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Movie> deleteMovie(@PathVariable int id) {
        Movie movieToBeDeleted = findMovieOrThrowException(id);

        this.movieRepository.deleteById(id);

        return ResponseEntity.ok(movieToBeDeleted);
    }

    @GetMapping("/{id}/screenings")
    public ResponseEntity<List<Screening>> getAllScreeningsOfOneMovie(@PathVariable int id) {
        Movie movie = findMovieOrThrowException(id);

        List<Screening> allScreenings = movie.getScreenings();

        return ResponseEntity.ok(allScreenings);
    }

    @PostMapping("/{id}/screenings")
    public ResponseEntity<Screening> createScreening(@PathVariable int id, @RequestBody Screening screening) {
        Movie movie = findMovieOrThrowException(id);

        validateScreeningOrThrowException(screening);

        movie.getScreenings().add(screening);

        Screening newScreening = this.movieRepository.save(movie).getScreenings().get(movie.getScreenings().size()-1);

        return new ResponseEntity<>(newScreening, HttpStatus.CREATED);
    }

    private void validateMovieOrThrowException(Movie movie) {
        if(movie.getTitle() == null || movie.getRating() == null || movie.getDescription() == null || movie.getRunTimeMins() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create a new movie, please check all fields are correct.");
        }
    }

    private Movie findMovieOrThrowException(int id) {
        return this.movieRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No movie with that id found."));
    }

    private void validateScreeningOrThrowException(Screening screening) {
        if(screening.getScreenNumber() < 0 || screening.getCapacity() < 0 || screening.getStartsAt() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create a screening for the specified movie, please check all fields are correct.");
        }
    }
}
