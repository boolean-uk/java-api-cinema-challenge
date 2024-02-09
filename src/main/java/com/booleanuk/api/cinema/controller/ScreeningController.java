package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.dto.CustomerDto;
import com.booleanuk.api.cinema.dto.ScreeningDto;
import com.booleanuk.api.cinema.model.Customer;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("movies/{movId}/screenings")
public class ScreeningController {
    @Autowired
    ScreeningRepository repository;

    @Autowired
    MovieRepository movieRepository;

    @GetMapping
    public List<ScreeningDto> getAllScreeningOffMovie(@PathVariable int movId) {
        return this.repository.findByMovieId(movId);
    }

    @PostMapping
    public ResponseEntity<ScreeningDto> createScreening(@PathVariable int movId, @RequestBody Screening screening) {
        if (screening.getScreenNumber() < 0 || screening.getCapacity() < 0 || screening.getStartsAt() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad request");
        }
        screening.setMovie(this.movieRepository.findById(movId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found")));
        Screening createdScreening = this.repository.save(screening);
        createdScreening.setTickets(new ArrayList<>());
        return new ResponseEntity<>(this.translateToDto(createdScreening), HttpStatus.CREATED);
    }

    public ScreeningDto translateToDto(Screening screening) {
        return new ScreeningDto(screening.getId(), screening.getScreenNumber(), screening.getCapacity(), screening.getStartsAt(), screening.getCreatedAt(), screening.getUpdatedAt());
    }
}
