package com.booleanuk.api.cinema.Controller;

import com.booleanuk.api.cinema.Model.Customer;
import com.booleanuk.api.cinema.Model.Movie;
import com.booleanuk.api.cinema.Repository.CustomerRepository;
import com.booleanuk.api.cinema.Repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@RestController
@RequestMapping("movies")
public class MovieController {

    @Autowired
    private MovieRepository repository;


    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie){
        try {
            return new ResponseEntity<Movie>(this.repository.save(movie),
                    HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create a new movie, please check all required fields are correct.");
        }


    }

    @GetMapping
    public List<Movie> getAll() {
        return this.repository.findAll();
    }


    @PutMapping("{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable int id,
                                                   @RequestBody Movie movie){
        Movie movieToUpdate=this.repository.findById(id).orElseThrow(
                ()->new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No movie with that ID found")
        );
        movieToUpdate.setTitle(movie.getTitle());
        movieToUpdate.setRating(movie.getRating());
        movieToUpdate.setDescription(movie.getDescription());
        movieToUpdate.setRuntimeMins(movie.getRuntimeMins());
        try{
            return new ResponseEntity<Movie>(this.repository.save(movieToUpdate
            ), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not update movie, please check all required fields are correct.");
        }




    }

    @DeleteMapping("{id}")
    public ResponseEntity<Movie> deleteMovie(@PathVariable int id){
        Movie movieToDelete=this.repository.findById(id).orElseThrow(
                ()->new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No movie with that ID found")
        );

        this.repository.delete(movieToDelete);
        return ResponseEntity.ok(movieToDelete);
    }
}
