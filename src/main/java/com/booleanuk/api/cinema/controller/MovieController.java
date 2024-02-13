package com.booleanuk.api.cinema.controller;


import com.booleanuk.api.cinema.model.*;
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

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
	DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss.SSSxxx");

	@GetMapping
	public ResponseEntity<CustomResponse<List<Movie>>> getAll() {
		return ResponseEntity.ok(new CustomResponse<>("success", movieRepository.findAll()));
	}

	@PostMapping
	public ResponseEntity<CustomResponse<?>> create(@RequestBody MovieDTO movieDTO) {
		Movie movie= new Movie(movieDTO.getTitle(),movieDTO.getRating(),movieDTO.getDescription(),movieDTO.getRuntimeMins());
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
		return new ResponseEntity<>(new CustomResponse<>("success", toDTO(newMovie)), HttpStatus.CREATED);
	}



	@PostMapping("{id}/screenings")
	public ResponseEntity<CustomResponse<?>> createScreening(@PathVariable int id, @RequestBody ScreeningDTO screening) {
		Movie movie = movieRepository.findById(id).orElse(null);
		if (movie == null) {
			return new ResponseEntity<>(new CustomResponse<>("error", Collections.singletonMap("message", "not found")), HttpStatus.NOT_FOUND);

		}
		if (screening.getScreenNumber() == 0 || screening.getStartsAt() == null || screening.getCapacity() == 0) {
			return new ResponseEntity<>(new CustomResponse<>("error", Collections.singletonMap("message", "bad request")), HttpStatus.BAD_REQUEST);

		}
		Screening newScreening = new Screening(movie, screening.getScreenNumber(), screening.getStartsAt(), screening.getCapacity());
		screeningRepository.save(newScreening);
		return new ResponseEntity<>(new CustomResponse<>("success", toDTO(newScreening)), HttpStatus.CREATED);

	}

	@GetMapping("{id}")
	public ResponseEntity<MovieDTO> get(@PathVariable int id) {
		Movie movie = movieRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found"));
		return ResponseEntity.ok(toDTO(movie));
	}

	@GetMapping("{id}/screenings")
	public ResponseEntity<CustomResponse<?>> getScreenings(@PathVariable int id) {
		Movie movie = movieRepository.findById(id).orElse(null);
		if (movie == null) {
			return new ResponseEntity<>(new CustomResponse<>("error", Collections.singletonMap("message", "not found")), HttpStatus.NOT_FOUND);

		}
		List<Screening> screenings = screeningRepository.findAllByMovie(movie);
		List<ScreeningDTO> screeningsDTO = new ArrayList<>();
		for (Screening screening : screenings) {
			screeningsDTO.add(toDTO(screening));
		}
		return ResponseEntity.ok(new CustomResponse<>("success", screeningsDTO));

	}

	@PutMapping("{id}")
	public ResponseEntity<CustomResponse<?>> update(@PathVariable int id, @RequestBody MovieDTO movieDTO) {
		Movie movie= new Movie(movieDTO.getTitle(),movieDTO.getRating(),movieDTO.getDescription(),movieDTO.getRuntimeMins());
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
		movieRepository.save(newMovie);
		return new ResponseEntity<>(new CustomResponse<>("success",toDTO(newMovie)), HttpStatus.CREATED);
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
		return ResponseEntity.ok(new CustomResponse<>("success", toDTO(movie)));

	}

	private ScreeningDTO toDTO(Screening screening) {
		System.out.println(screening.getStartsAt().getOffset());
		return new ScreeningDTO(
				screening.getId(),
				screening.getMovie(),
				screening.getScreenNumber(),
				screening.getStartsAt().format(outputFormatter),
				screening.getCapacity(),
				screening.getCreatedAt().format(outputFormatter),
				screening.getUpdatedAt().format(outputFormatter),
				null);
	}
	private MovieDTO toDTO(Movie movie)
	{        return new MovieDTO(
			movie.getId(),
			movie.getTitle(),
			movie.getRating(),
			movie.getDescription(),
			movie.getRuntimeMins(),
			null,
			movie.getCreatedAt().format(outputFormatter),
			movie.getUpdatedAt().format(outputFormatter)
	);}


}
