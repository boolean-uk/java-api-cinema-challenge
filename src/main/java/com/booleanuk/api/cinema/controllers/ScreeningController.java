package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Movie;
import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.repositories.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("movies")
public class ScreeningController {
    @Autowired
    private ScreeningRepository repository;

    @PostMapping("{id}/screenings")
    public ResponseEntity<Screening> create(
            @PathVariable int id,
            @RequestBody Screening screening)
    {
        screening.setMovieId(id);
        return new ResponseEntity<>(this.repository.save(screening), HttpStatus.CREATED);
    }

    @GetMapping("{id}/screenings")
    public ResponseEntity<List<Screening>> getAll(@PathVariable int id) {
        return ResponseEntity.ok(this.repository.findAllByMovieId(id));
    }
}
