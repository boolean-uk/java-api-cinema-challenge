package com.booleanuk.api.cinema.controller;


import com.booleanuk.api.cinema.model.CustomResponse;
import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.CustomerRepository;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.repository.TicketRepository;
import jakarta.transaction.Transactional;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("movies")
public class MovieController {
	@Autowired
	MovieRepository movieRepository;

	@Autowired
	ScreeningRepository screeningRepository;

	@Autowired
	TicketRepository ticketRepository;

	@GetMapping
	public ResponseEntity<CustomResponse<List<Movie>>> getAll() {
		return ResponseEntity.ok(new CustomResponse<>("success", movieRepository.findAll()));
	}

	@PostMapping
	public ResponseEntity<CustomResponse<?>> create(@RequestBody Movie movie) {
		if (movie.getTitle() == null || movie.getRating() == null || movie.getDescription() == null || movie.getRuntimeMins() == 0) {
			return new ResponseEntity<>(new CustomResponse<>("error", Collections.singletonMap("message", "bad request")), HttpStatus.BAD_REQUEST);
		}
		Movie newMovie = movieRepository.save(movie);

		if (movie.getScreenings() != null) {
			for (Screening s : movie.getScreenings()) {
				if (s.getScreenNumber() == 0 || s.getStartsAt() == null || s.getCapacity() == 0) {
					return new ResponseEntity<>(new CustomResponse<>("error", Collections.singletonMap("message", "bad request")), HttpStatus.BAD_REQUEST);
				}
				s.setMovie(newMovie);
				screeningRepository.save(s);
			}
		}
		return new ResponseEntity<>(new CustomResponse<>("success", movieRepository.save(newMovie)), HttpStatus.CREATED);
	}

	@PostMapping("{id}/screenings")
	public ResponseEntity<CustomResponse<?>> createScreening(@PathVariable int id, @RequestBody Screening screening) {
		Movie movie = movieRepository.findById(id).orElse(null);
		if (movie == null) {
			return new ResponseEntity<>(new CustomResponse<>("error", Collections.singletonMap("message", "not found")), HttpStatus.NOT_FOUND);

		}
		if (screening.getScreenNumber() == 0 || screening.getStartsAt() == null || screening.getCapacity() == 0) {
			return new ResponseEntity<>(new CustomResponse<>("error", Collections.singletonMap("message", "bad request")), HttpStatus.BAD_REQUEST);

		}
		Screening newScreening = new Screening(movie, screening.getScreenNumber(), screening.getStartsAt(), screening.getCapacity());
		return new ResponseEntity<>(new CustomResponse<>("success", screeningRepository.save(newScreening)), HttpStatus.CREATED);

	}

	@GetMapping("{id}")
	public ResponseEntity<Movie> get(@PathVariable int id) {
		Movie movie = movieRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
		return ResponseEntity.ok(movie);
	}

	@GetMapping("{id}/screenings")
	public ResponseEntity<CustomResponse<?>> getScreenings(@PathVariable int id) {
		Movie movie = movieRepository.findById(id).orElse(null);
		if (movie == null) {
			return new ResponseEntity<>(new CustomResponse<>("error", Collections.singletonMap("message", "not found")), HttpStatus.NOT_FOUND);

		}
		List <Screening> screenings = screeningRepository.findAllByMovie(movie);
		return ResponseEntity.ok(new CustomResponse<>("success",screenings));

	}

	@PutMapping("{id}")
	public ResponseEntity<CustomResponse<?>> update(@PathVariable int id, @RequestBody Movie movie) {
		Movie newMovie = movieRepository.findById(id).orElse(null);
		if (newMovie == null) {
			return new ResponseEntity<>(new CustomResponse<>("error", Collections.singletonMap("message", "not found")), HttpStatus.NOT_FOUND);
		}
		if (movie.getTitle() == null && movie.getDescription() == null && movie.getRating() == null) {
			return new ResponseEntity<>(new CustomResponse<>("error", Collections.singletonMap("message", "bad request")), HttpStatus.BAD_REQUEST);
		}

		if (movie.getTitle() != null) {
			newMovie.setTitle(movie.getTitle());
		}
		if (movie.getDescription() != null) {
			newMovie.setDescription(movie.getDescription());
		}
		if (movie.getRating() != null) {
			newMovie.setRating(movie.getRating());
		}
		return new ResponseEntity<>(new CustomResponse<>("success", movieRepository.save(newMovie)), HttpStatus.CREATED);
	}

	@DeleteMapping("{id}")
	@Transactional
	public ResponseEntity<CustomResponse<?>> delete(@PathVariable int id) {
		Movie movie = movieRepository.findById(id).orElse(null);
		if (movie == null) {
			return new ResponseEntity<>(new CustomResponse<>("error", Collections.singletonMap("message", "not found")), HttpStatus.NOT_FOUND);
		}
		for (Screening screening : movie.getScreenings()) {
			ticketRepository.deleteByScreening(screening);
			screeningRepository.delete(screening);
		}
		movieRepository.delete(movie);
		return ResponseEntity.ok(new CustomResponse<>("success", movie));

	}

}
