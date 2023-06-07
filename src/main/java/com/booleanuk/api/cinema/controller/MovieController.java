package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.dto.ScreeningViewDTO;
import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.service.ScreeningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("movies")
public class MovieController {

    private final MovieRepository movieRepository;
    private final ScreeningService screeningService;

    @Autowired
    public MovieController(MovieRepository movieRepository, ScreeningService screeningService){
        this.movieRepository = movieRepository;
        this.screeningService = screeningService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Movie create(@RequestBody Movie movie){

        try {
            return movieRepository.save(movie);
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create movie, please check all required fields are correct");
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Movie> getAll(){
        return movieRepository.findAll();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Movie update(@PathVariable int id, @RequestBody Movie movie){
        Movie movieToUpdate = movieRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "No movies matching that id were found"));

        movieToUpdate.setTitle(movie.getTitle());
        movieToUpdate.setRating(movie.getRating());
        movieToUpdate.setDescription(movie.getDescription());
        movieToUpdate.setRuntimeMins(movie.getRuntimeMins());

        try {return movieRepository.save(movieToUpdate);}
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Could not update the movie's details, please check all required fields are correct");
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Movie delete(@PathVariable int id) {
        Movie movieToDelete = movieRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No movies matching that id were found"));
        movieRepository.delete(movieToDelete);

        return movieToDelete;
    }

    @PostMapping("/{id}/screenings")
    @ResponseStatus(HttpStatus.CREATED)
    public Screening createScreening(@PathVariable int id, @RequestBody Screening screening){
        return screeningService.create(id, screening);
    }

    @GetMapping("/{id}/screenings")
    @ResponseStatus(HttpStatus.OK)
    public List<ScreeningViewDTO> getAllScreeningsForMovie(@PathVariable int id){
        return screeningService.getAllForMovie(id);
    }
}
