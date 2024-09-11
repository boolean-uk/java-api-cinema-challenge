package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("screening")
public class ScreeningController {

    @Autowired
    ScreeningRepository repository;

    @GetMapping
    public ResponseEntity<List<Screening>> getAll() {
        return ResponseEntity.ok(this.repository.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Screening> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(getScreening(id));
    }

    @PostMapping
    public ResponseEntity<Screening> create(@RequestBody Screening screening) {
        screening.setCreatedAt(new Date());
        return new ResponseEntity<>(this.repository.save(screening), HttpStatus.CREATED);
    }


    /*
    @PutMapping("{id}")
    public ResponseEntity<Screening> update(@PathVariable Integer id, Screening updatedScreening) {
        Screening screening = getScreening(id);
        screening.setScreenNumber(updatedScreening.getScreenNumber());
        screening.setCapacity(updatedScreening.getCapacity());
        screening.setStartsAt(updatedScreening.getStartsAt());
        screening.setUpdatedAt(new Date());

        screening.setCreatedAt(screening.getCreatedAt());

        return new ResponseEntity<>(this.repository.save(screening), HttpStatus.CREATED);
    }
    */

    private Screening getScreening(Integer id) {
        return this.repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find screeing with given ID"));
    }
}
