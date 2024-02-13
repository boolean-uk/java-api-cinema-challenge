package com.booleanuk.api.cinema.controller;


import com.booleanuk.api.cinema.helpers.ErrorResponse;
import com.booleanuk.api.cinema.helpers.MovieDTOMapper;
import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.booleanuk.api.cinema.helpers.CustomResponse;

import java.time.LocalDateTime;
import java.time.ZoneOffset;


@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    MovieRepository movieRepository;
    @Autowired
    ScreeningRepository screeningRepository;
    @Autowired
    TicketRepository ticketRepository;

    private final MovieDTOMapper movieDTOMapper;

    public MovieController(MovieDTOMapper movieDTOMapper) {
        this.movieDTOMapper = movieDTOMapper;
    }

    @GetMapping
    public ResponseEntity<CustomResponse> getAll(){
        return new ResponseEntity<>(new CustomResponse("success", movieRepository.findAll()
                .stream().map(movieDTOMapper)), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse> get(@PathVariable int id){
        if(!movieRepository.existsById(id)){
            return new ResponseEntity<>(new CustomResponse("error", new ErrorResponse("No movie with that id were found")), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new CustomResponse("success", movieRepository.findById(id).stream().map(movieDTOMapper)), HttpStatus.OK);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CustomResponse> create(@RequestBody Movie movie){
        if(movie.getTitle() == null || movie.getDescription() == null || movie.getRating() == null){
            return new ResponseEntity<>(new CustomResponse("error", new ErrorResponse("Could not update movie, please check all required fields are correct")), HttpStatus.BAD_REQUEST);
        }
        movieRepository.save(movie);
        // save screening data
        for(Screening s : movie.getScreenings()){
            s.setMovie(movie);
            screeningRepository.save(s);
        }
        return new ResponseEntity<>(new CustomResponse("success", movieRepository.findById(movie.getId()).stream().map(movieDTOMapper)), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CustomResponse> update(@RequestBody Movie movie, @PathVariable int id){
        if(!movieRepository.existsById(id)){
            return new ResponseEntity<>(new CustomResponse("error", new ErrorResponse("No movie with that id were found")), HttpStatus.NOT_FOUND);
        }
        Movie existingMovie = movieRepository
                .findById(id).get();
        existingMovie.setTitle(movie.getTitle());
        existingMovie.setDescription(movie.getDescription());
        existingMovie.setRating(movie.getRating());
        existingMovie.setRuntimeMins(movie.getRuntimeMins());
        existingMovie.setScreenings(movie.getScreenings());
        existingMovie.setUpdatedAt(LocalDateTime.now(ZoneOffset.UTC));

        if(existingMovie.getTitle() == null || existingMovie.getRating() == null || existingMovie.getRuntimeMins() == 0 || existingMovie.getDescription() == null){
            return new ResponseEntity<>(new CustomResponse("error", new ErrorResponse("Could not update movie, please check all required fields are correct")), HttpStatus.BAD_REQUEST);
        }
        movieRepository.save(existingMovie);
        return new ResponseEntity<>(new CustomResponse("success", movieRepository.findById(existingMovie.getId()).stream().map(movieDTOMapper)), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomResponse> delete(@PathVariable int id){
        if(!movieRepository.existsById(id)){
            return new ResponseEntity<>(new CustomResponse("error", new ErrorResponse("No movie with that id were found")), HttpStatus.NOT_FOUND);
        }
        Movie movie = movieRepository
                .findById(id).get();

        // delete all tickets and screenings for this movie

        for (int i = 0; i < ticketRepository.findAll().size(); i++) {
            if(ticketRepository.findAll().get(i).getScreening().getMovie().getId() == id){
                ticketRepository.delete(ticketRepository.findAll().get(i));
            }
        }
        for (int i = 0; i < screeningRepository.findAll().size(); i++) {
            if(screeningRepository.findAll().get(i).getMovie().getId() == id){
                screeningRepository.delete(screeningRepository.findAll().get(i));
            }
        }

        CustomResponse response = new CustomResponse("success", movieRepository.findById(id).stream().map(movieDTOMapper));
        movieRepository.delete(movie);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
