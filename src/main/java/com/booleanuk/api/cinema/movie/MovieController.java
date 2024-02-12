package com.booleanuk.api.cinema.movie;

import com.booleanuk.api.cinema.response.*;

import com.booleanuk.api.cinema.response.Error;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("movies")
public class MovieController {
    LocalDateTime currentTime = LocalDateTime.now();

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping
    public ResponseEntity<Response> getAll() {
        return new ResponseEntity<>(new MovieListResponse(this.movieRepository.findAll()), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Response> getMovie(@PathVariable int id) {
        Movie movie = this.movieRepository
                .findById(id)
                .orElse(null);
        if (movie == null) {
            return new ResponseEntity<>(new ErrorResponse(new Error("Movie not found")), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new MovieResponse(movie), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Response> createMovie(@RequestBody Movie movie) {
        //String regex= "/^[a-zA-Z\\s]*$/";

        if(movie.getTitle().isEmpty() || movie.getDescription().isEmpty() || movie.getRating().isEmpty()){
            return new ResponseEntity<>(new ErrorResponse(new Error("Bad Request")), HttpStatus.BAD_REQUEST);
        }

        movie.setCreatedAt(currentTime);
        movie.setUpdatedAt(currentTime);
        this.movieRepository.save(movie);
        return new ResponseEntity<>(new MovieResponse(movie), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteMovie(@PathVariable int id) {
        Movie deleted = this.movieRepository
                .findById(id)
                .orElse(null);

        if (deleted == null) {
            return new ResponseEntity<>(new ErrorResponse(new Error("Movie not found")), HttpStatus.NOT_FOUND);
        }
        this.movieRepository.delete(deleted);
        return new ResponseEntity<>(new MovieResponse(deleted), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateMovie (@PathVariable int id, @RequestBody Movie movie) {

        Movie movieToUpdate = this.movieRepository
                .findById(id)
                .orElse(null);

        if (movieToUpdate == null) {
            return new ResponseEntity<>(new ErrorResponse(new Error("Movie not found")), HttpStatus.NOT_FOUND);
        }

        if(movie.getTitle().isEmpty() || movie.getDescription().isEmpty() || movie.getRating().isEmpty()){
            return new ResponseEntity<>(new ErrorResponse(new Error("Bad Request")), HttpStatus.BAD_REQUEST);
        }

        movieToUpdate.setTitle(movie.getTitle());
        movieToUpdate.setRating(movie.getRating());
        movieToUpdate.setDescription(movie.getDescription());
        movieToUpdate.setRunTimeMins(movie.getRunTimeMins());
        movieToUpdate.setUpdatedAt(currentTime);
        this.movieRepository.save(movieToUpdate);
        return new ResponseEntity<>(new MovieResponse(movieToUpdate), HttpStatus.OK);
    }

}
