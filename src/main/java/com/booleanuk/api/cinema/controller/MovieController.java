package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("movies")
public class MovieController {
    @Autowired
    private MovieService movieService;
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<Movie> getAll(){
        return movieService.getMovies();
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Movie get(@PathVariable(name="id") long id){
        return movieService.getMovie(id);
    }
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Movie update(@PathVariable(name="id") long id,@RequestBody Movie movie){
        return movieService.updateMovie(id,movie);
    }
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Movie create(@RequestBody Movie movie){
        return movieService.createMovie(movie);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Movie delete(@PathVariable(name="id") long id){
        return movieService.deleteMovie(id);
    }
}
