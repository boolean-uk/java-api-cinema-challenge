package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.repositories.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/screenings")
public class ScreeningController {

    @Autowired
    private ScreeningRepository screeningRepository;

    @GetMapping
    public List<Screening> getAllScreenings() {
        return screeningRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Screening> getScreeningById(@PathVariable int id) {
        return screeningRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Screening createScreening(@RequestBody Screening screening) {
        return screeningRepository.save(screening);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Screening> updateScreening(@PathVariable int id, @RequestBody Screening screeningDetails) {
        return screeningRepository.findById(id)
                .map(screening -> {
                    screening.setScreenNumber(screeningDetails.getScreenNumber());
                    screening.setStartsAt(screeningDetails.getStartsAt());
                    screening.setCapacity(screeningDetails.getCapacity());
                    screening.setCreatedAt(screeningDetails.getCreatedAt());
                    screening.setUpdatedAt(screeningDetails.getUpdatedAt());
                    return ResponseEntity.ok(screeningRepository.save(screening));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteScreening(@PathVariable int id) {
        return screeningRepository.findById(id)
                .map(screening -> {
                    screeningRepository.delete(screening);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}