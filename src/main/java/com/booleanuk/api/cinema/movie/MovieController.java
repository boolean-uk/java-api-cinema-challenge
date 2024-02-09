package com.booleanuk.api.cinema.movie;

import com.booleanuk.api.cinema.screening.Screening;
import com.booleanuk.api.cinema.screening.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("movies")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ScreeningRepository screeningRepository;


    @GetMapping
    public List<Movie> getAllMovies(){
        return this.movieRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie) {

        if (isInvalidRequest(movie)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update the movie, please check all required fields are correct");
        }
        Movie createdMovie = this.movieRepository.save(movie);
//        createdMovie.setScreenings(new ArrayList<>());
        return new ResponseEntity<>(createdMovie, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Movie> deleteMovie(@PathVariable int id) {
        Movie movieToDelete = this.getAMovie(id);
        this.movieRepository.delete(movieToDelete);
//        movieToDelete.setScreenings(new ArrayList<Screening>());
        return ResponseEntity.ok(movieToDelete);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> deleteMovieById(@PathVariable int id, @RequestBody Movie movie){


        if (isInvalidRequest(movie)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update the movie, please check all required fields are correct");
        }

        Movie movieToUpdate = this.getAMovie(id);
        movieToUpdate.setTitle(movie.getTitle());
        movieToUpdate.setRating(movie.getRating());
        movieToUpdate.setDescription(movie.getDescription());
        movieToUpdate.setRuntimeMins(movie.getRuntimeMins());
        movieToUpdate.setUpdatedAt(LocalDateTime.now());
        return new ResponseEntity<>(this.movieRepository.save(movieToUpdate), HttpStatus.CREATED);
    }

    //SCREENINGS

//    @GetMapping("/{id}/screenings")
//    public List<Screening> getScreenings(@PathVariable int id ){
//        Movie movieToCheckScreeningsFor = getAMovie(id);
//        return movieToCheckScreeningsFor.getScreenings();
//    }

    @PostMapping("/{id}/screenings")
    public ResponseEntity<Screening> getScreenings(@PathVariable int id, @RequestBody Screening screening){
        Movie movieToAddScreeningFor = getAMovie(id);
        Screening createdScreening = screeningRepository.save(screening);
        createdScreening.setMovie(movieToAddScreeningFor);
        return new ResponseEntity<>(createdScreening, HttpStatus.CREATED);
    }


    private Movie getAMovie(int id){
        return this.movieRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No movie with that id were found"));
    }

    private boolean isInvalidRequest(Movie movie){
        return movie.getTitle() == null || movie.getRating() == null || movie.getDescription() == null || movie.getRuntimeMins() == null;
    }

}
