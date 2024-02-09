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
@RequestMapping("movies/{id}/screenings")
public class ScreeningController {
    @Autowired
    private ScreeningRepository screeningRepository;

    @PostMapping
    public ResponseEntity<Screening> createScreening(@PathVariable int id, @RequestBody Screening screening) {
        this.checkHasRequiredFields(screening);
        screening.setMovie_id(id);
        screening.setCreatedAt(new Date());
        screening.setUpdatedAt(new Date());
        return new ResponseEntity<>(this.screeningRepository.save(screening), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Screening> getAllScreeningsForMovie(@PathVariable int id) {
        return this.screeningRepository.findAll().stream().filter(screening -> screening.getMovie_id() == id).toList();
    }

    // Method to check if all required fields are contained in the request, used in createScreening()
    private void checkHasRequiredFields(Screening screening) {
        if (screening.getScreenNumber() <= 0 || screening.getCapacity() <= 0 || screening.getStartsAt() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please check all required fields are correct.");
        }
    }
}
