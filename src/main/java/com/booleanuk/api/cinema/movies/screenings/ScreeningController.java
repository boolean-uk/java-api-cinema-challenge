package com.booleanuk.api.cinema.movies.screenings;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class ScreeningController  {
  private final ScreeningRepository repository;

  public ScreeningController(ScreeningRepository repository) {
    this.repository = repository;
  }

  @PostMapping(value = "movies/{id}/screenings")
  public ResponseEntity<Screening> postScreening(@PathVariable int id, @RequestBody Screening screening) {
    try {
      return ResponseEntity.status(HttpStatus.CREATED).body(this.repository.save(screening));
    } catch (DataIntegrityViolationException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
  }

  @GetMapping(value = "movies/{id}/screenings")
  public ResponseEntity<Screening> getScreening(@PathVariable int id) {
    return this.repository.findById(id)
        .map(ResponseEntity::ok)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No screening with id '" + id + "' was found"));
  }
}