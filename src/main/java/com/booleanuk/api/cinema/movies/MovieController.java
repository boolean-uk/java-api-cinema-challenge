package com.booleanuk.api.cinema.movies;

import com.booleanuk.api.cinema.screenings.Screening;
import com.booleanuk.api.cinema.screenings.ScreeningRepository;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("movies")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;



    @Autowired
    private ModelMapper modelMapper;


    @GetMapping
    public List<Movie> getAll() {
         return this.movieRepository.findAll();

    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getOne(@PathVariable int id) {
        return new ResponseEntity<>(
                this.movieRepository
                        .findById(id)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found")),
                HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<Movie> addOne(@Valid @RequestBody Movie movie){
        for (Screening screening : movie.getScreenings()) {
            screening.setMovie(movie);
        }
        Movie savedMovie = this.movieRepository.save(movie);
        return new ResponseEntity<>(savedMovie, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateOne(@PathVariable int id, @Valid @RequestBody Movie movie){
        Movie movieToUpdate = this.movieRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        movieToUpdate.setTitle(movie.getTitle());
        movieToUpdate.setRating(movie.getRating());
        movieToUpdate.setDescription(movie.getDescription());
        movieToUpdate.setRuntimeMins(movie.getRuntimeMins());

        return new ResponseEntity<>(movieToUpdate, HttpStatus.CREATED);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Movie> deleteOne(@PathVariable int id){
        Movie movie = this.movieRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        try{
            this.movieRepository.delete(movie);
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Movie has active screening(s)");
        }
        return new ResponseEntity<>(movie, HttpStatus.OK);

    }
}
