package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.GenericResponse;
import com.booleanuk.api.cinema.dto.MovieViewDTO;
import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.service.MovieService;
import com.booleanuk.api.cinema.service.ScreeningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("movies")
public class MovieController {

    private final ScreeningService screeningService;
    private final MovieService movieService;

    @Autowired
    public MovieController(ScreeningService screeningService, MovieService movieService){
        this.screeningService = screeningService;
        this.movieService = movieService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GenericResponse<MovieViewDTO> create(@RequestBody Movie movie){
        return new GenericResponse<MovieViewDTO>()
                .from(movieService.create(movie));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public GenericResponse<List<MovieViewDTO>> getAll(){
        return new GenericResponse<List<MovieViewDTO>>()
                .from(movieService.getAll());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public GenericResponse<MovieViewDTO> update(@PathVariable int id, @RequestBody Movie movie){
        return new GenericResponse<MovieViewDTO>()
                .from(movieService.update(id, movie));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GenericResponse<MovieViewDTO> delete(@PathVariable int id) {
        return new GenericResponse<MovieViewDTO>()
                .from(movieService.delete(id));
    }

    @PostMapping("/{id}/screenings")
    @ResponseStatus(HttpStatus.CREATED)
    public GenericResponse<Screening> createScreening(@PathVariable int id, @RequestBody Screening screening){
        return new GenericResponse<Screening>()
                .from(screeningService.create(id, screening));
    }

    @GetMapping("/{id}/screenings")
    @ResponseStatus(HttpStatus.OK)
    public GenericResponse<List<Screening>> getAllScreeningsForMovie(@PathVariable int id){
        return new GenericResponse<List<Screening>>()
                .from(screeningService.getAllForMovie(id));
    }
}
