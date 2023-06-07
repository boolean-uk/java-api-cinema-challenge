package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.DTOs.CustomerNoRelationsDTO;
import com.booleanuk.api.cinema.models.Movie;
import com.booleanuk.api.cinema.models.DTOs.MovieNoRelationsDTO;
import com.booleanuk.api.cinema.repositories.MovieRepository;
import com.booleanuk.api.cinema.repositories.ScreeningRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("movies")
public class MovieController {
    private record MovieSingleDTO (String status, MovieNoRelationsDTO data) {}
    private record MovieListDTO (String status, List<MovieNoRelationsDTO> data) {}
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ScreeningRepository screeningRepository;
    @Autowired
    private ModelMapper modelMapper;

    // ------------------ ENDPOINTS ------------------//
    //region // POST //
    @PostMapping
    public ResponseEntity<MovieSingleDTO> create(@RequestBody Movie movie) {
        Movie movieToCreate = movie;

        this.movieRepository.save(movieToCreate);

        if(movieToCreate.getScreenings() != null) {
            movieToCreate.getScreenings().forEach(x -> x.setMovie(movieToCreate));
            this.screeningRepository.saveAll(movieToCreate.getScreenings());
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new MovieSingleDTO("success", modelMapper
                        .map(movieToCreate, MovieNoRelationsDTO.class)
                ));
    }
    //endregion
    //region // GET //
    @GetMapping
    public ResponseEntity<MovieListDTO> getAll() {
        return ResponseEntity
                .ok(new MovieListDTO("success", this.movieRepository.findAll().stream()
                        .map(x -> modelMapper
                                .map(x, MovieNoRelationsDTO.class))
                        .collect(Collectors.toList())
                ));
    }
    //endregion
    //region // PUT //
    @PutMapping("/{id}")
    public ResponseEntity<MovieSingleDTO> update(@PathVariable int id, @RequestBody Movie movie) {
        Movie movieToUpdate = this.movieRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Movie with this id doesn't exist."
                ));

        boolean hasChanged = false;
        if(!movieToUpdate.getTitle().equals(movie.getTitle())) {
            movieToUpdate.setTitle(movie.getTitle());
            hasChanged = true;
        }
        if(!movieToUpdate.getRating().equals(movie.getRating())) {
            movieToUpdate.setRating(movie.getRating());
            hasChanged = true;
        }
        if(!movieToUpdate.getDescription().equals(movie.getDescription())) {
            movieToUpdate.setDescription(movie.getDescription());
            hasChanged = true;
        }
        if(!(movieToUpdate.getRuntimeMins() == movie.getRuntimeMins())) {
            movieToUpdate.setRuntimeMins(movie.getRuntimeMins());
            hasChanged = true;
        }

        if (hasChanged) {
            movieToUpdate.setUpdatedAt(ZonedDateTime
                    .ofInstant(Instant.now(), ZoneId.of("CET"))
                    .format(DateTimeFormatter.ISO_DATE_TIME)
                    .toString());

            this.movieRepository.save(movieToUpdate);
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new MovieSingleDTO("success", modelMapper
                        .map(movieToUpdate, MovieNoRelationsDTO.class)
                ));
    }
    //endregion
    //region // DELETE //
    @DeleteMapping("/{id}")
    public ResponseEntity<MovieSingleDTO> delete(@PathVariable int id) {
        Movie movieToDelete = this.movieRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, 
                        "Movie with this id doesn't exist."
                ));

        this.movieRepository.delete(movieToDelete);
        return ResponseEntity
                .ok(new MovieSingleDTO("success", modelMapper
                .map(movieToDelete, MovieNoRelationsDTO.class)
                ));
    }
    //endregion
}