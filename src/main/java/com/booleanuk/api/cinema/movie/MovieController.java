package com.booleanuk.api.cinema.movie;

import com.booleanuk.api.cinema.response.*;
import com.booleanuk.api.cinema.screening.Screening;
import com.booleanuk.api.cinema.screening.ScreeningRepository;
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
    @Autowired
    private ScreeningRepository screeningRepository;

    @PostMapping
    public ResponseEntity<?> createMovie(@RequestBody Movie movie) {
        if(movie.getTitle() == null || movie.getRating() == null || movie.getDescription() == null || movie.getRuntimeMins() < 0) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Could not create a new movie, please check all fields are correct.");

            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        Movie newMovie = this.movieRepository.save(movie);  //Setta screenings innan spara?

        if(movie.getScreenings() == null) {
            newMovie.setScreenings(new ArrayList<Screening>());
        }

        MovieResponse movieResponse = new MovieResponse();
        movieResponse.set(newMovie);

        return new ResponseEntity<>(movieResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<MovieListResponse> getAllMovies() {
        List<Movie> allMovies = this.movieRepository.findAll();

        MovieListResponse movieListResponse = new MovieListResponse();
        movieListResponse.set(allMovies);

        return ResponseEntity.ok(movieListResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMovie(@PathVariable int id, @RequestBody Movie movie) {
        Movie movieToUpdate = this.movieRepository.findById(id).orElse(null);

        if(movie == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("No movie with that id found.");

            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        if(movie.getTitle() != null) {
            movieToUpdate.setTitle(movie.getTitle());
        }
        if(movie.getRating() != null) {
            movieToUpdate.setRating(movie.getRating());
        }
        if(movie.getDescription() != null) {
            movieToUpdate.setDescription(movie.getDescription());
        }
        if(movie.getRuntimeMins() > 0) {
            movieToUpdate.setRuntimeMins(movie.getRuntimeMins());
        }

        Movie updatedMovie = this.movieRepository.save(movieToUpdate);

        MovieResponse movieResponse = new MovieResponse();
        movieResponse.set(updatedMovie);

        return new ResponseEntity<>(movieResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable int id) {
        Movie movieToBeDeleted = this.movieRepository.findById(id).orElse(null);

        if(movieToBeDeleted == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("No movie with that id found.");

            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        this.movieRepository.deleteById(id);

        MovieResponse movieResponse = new MovieResponse();
        movieResponse.set(movieToBeDeleted);

        return ResponseEntity.ok(movieResponse);
    }

    @PostMapping("/{id}/screenings")
    public ResponseEntity<?> createScreening(@PathVariable int id, @RequestBody Screening screening) {
        Movie movie = this.movieRepository.findById(id).orElse(null);

        if(movie == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("No movie with that id found.");

            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        if(screening.getScreenNumber() < 0 || screening.getCapacity() < 0 || screening.getStartsAt() == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("Could not create a screening for the specified movie, please check all fields are correct.");

            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        //En screening behöver en movie. movie_id column i screenings table får ej vara null
        screening.setMovie(movie);

        Screening newScreening = this.screeningRepository.save(screening);

        List<Screening> screenings = movie.getScreenings();
        screenings.add(newScreening);
        movie.setScreenings(screenings);

        ScreeningResponse screeningResponse = new ScreeningResponse();
        screeningResponse.set(newScreening);

        return new ResponseEntity<>(screeningResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{id}/screenings")
    public ResponseEntity<?> getAllScreeningsOfOneMovie(@PathVariable int id) {
        Movie movie = this.movieRepository.findById(id).orElse(null);

        if(movie == null) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("No movie with that id found.");

            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        List<Screening> allScreenings = movie.getScreenings();

        ScreeningListResponse screeningListResponse = new ScreeningListResponse();
        screeningListResponse.set(allScreenings);

        return ResponseEntity.ok(screeningListResponse);
    }
}
