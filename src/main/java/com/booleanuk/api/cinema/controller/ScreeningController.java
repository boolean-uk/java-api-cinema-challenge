package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("screenings")
public class ScreeningController {

	@Autowired
	ScreeningRepository screeningRepository;
	@Autowired
	MovieRepository movieRepository;

	@GetMapping
	public List<Screening> getAll() {
		return screeningRepository.findAll();
	}

	@PostMapping
	public ResponseEntity<Screening> create(@RequestBody Screening screening) {
		Movie movie = movieRepository.findById(screening.getMovie().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
		Screening newScreening = new Screening(movie, screening.getScreenNumber(), screening.getStartsAt(), screening.getCapacity());
		return new ResponseEntity<>(screeningRepository.save(newScreening), HttpStatus.CREATED);
	}

	@GetMapping("{id}")
	public ResponseEntity<Screening> get(@PathVariable int id) {
		Screening screening = screeningRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
		return ResponseEntity.ok(screening);
	}

	@PutMapping("{id}")
	public ResponseEntity<Screening> update(@PathVariable int id, @RequestBody Screening screening) {
		Screening newScreening = screeningRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
		newScreening.setMovie(screening.getMovie());
		newScreening.setScreenNumber(screening.getScreenNumber());
		newScreening.setStartsAt(screening.getStartsAt());
		newScreening.setCapacity(screening.getCapacity());
		return new ResponseEntity<>(screeningRepository.save(newScreening), HttpStatus.CREATED);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Screening> delete(@PathVariable int id) {
		Screening screening = screeningRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
		screeningRepository.delete(screening);
		return ResponseEntity.ok(screening);

	}

}
