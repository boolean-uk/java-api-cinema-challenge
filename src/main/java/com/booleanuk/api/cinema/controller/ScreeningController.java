package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.model.ScreeningDTO;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/screenings")
public class ScreeningController {
    @Autowired
    ScreeningRepository screeningRepository;

    @PostMapping
    public ResponseEntity<Screening> addScreening(@RequestBody ScreeningDTO screeningDTO) throws ResponseStatusException {
        try {
            return new ResponseEntity<>(this.screeningRepository.save(convertFromDTO(screeningDTO)), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "An error occurred when attempting to create a screening: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Screening>> getAllScreenings() throws ResponseStatusException {
        return ResponseEntity.ok(this.screeningRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Screening> getScreeningById(@PathVariable (name = "id") int id) throws ResponseStatusException {
        return ResponseEntity.ok(findScreeningById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Screening> updateScreening(@PathVariable (name = "id") int id, @RequestBody ScreeningDTO screeningDTO) throws ResponseStatusException {
        Screening screeningToUpdate = findScreeningById(id);
        try {
            Screening convertedScreening = convertFromDTO(screeningDTO);
            screeningToUpdate.setScreenNumber(convertedScreening.getScreenNumber());
            screeningToUpdate.setCapacity(convertedScreening.getCapacity());
            screeningToUpdate.setStartsAt(convertedScreening.getStartsAt());
            screeningToUpdate.setUpdatedAt(LocalDateTime.now());
            return new ResponseEntity<>(this.screeningRepository.save(screeningToUpdate), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "An error occurred when attempting to update screening: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Screening> deleteScreening(@PathVariable (name = "id") int id) throws ResponseStatusException {
        Screening screeningToDelete = findScreeningById(id);
        this.screeningRepository.delete(screeningToDelete);
        return ResponseEntity.ok(screeningToDelete);
    }

    private Screening findScreeningById(int id) throws ResponseStatusException {
        return this.screeningRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Screening with the provided ID does not exist."));
    }

    private Screening convertFromDTO(ScreeningDTO screeningDTO) {
        String formattedDate = screeningDTO.getStartsAt().replace(" ", "T");
        OffsetDateTime startsAt = OffsetDateTime.parse(formattedDate, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        return new Screening(screeningDTO.getScreenNumber(), screeningDTO.getCapacity(), startsAt);
    }
}
