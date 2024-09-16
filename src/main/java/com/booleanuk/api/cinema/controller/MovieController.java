package com.booleanuk.api.cinema.controller;


import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.ResponseObject;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.view.MovieRepository;
import com.booleanuk.api.cinema.view.ScreeningRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("movies")
public class MovieController {
	private MovieRepository movieRepo;
	private ScreeningRepository screeningRepo;

	private HashMap<String, String> errorMessage;

	public MovieController(MovieRepository movieRepository, ScreeningRepository screeningRepo){
		this.movieRepo = movieRepository;
		this.screeningRepo = screeningRepo;
		this.errorMessage= new HashMap<>();
		errorMessage.put("message", "Failed");
	}

	@GetMapping //all
	public ResponseEntity<ResponseObject<List<Movie>>> getAll(){
		return new ResponseEntity<>(new ResponseObject<>("Success.", movieRepo.findAll()), HttpStatus.OK);
	}


	@PostMapping
	public ResponseEntity<ResponseObject<?>> postOne(@RequestBody Movie movie){
		try {
			checkIfValidMovie(movie);
			// throws error if invalid.
		} catch (Exception e) {
			return new ResponseEntity<>(new ResponseObject<>("Failed.", errorMessage), HttpStatus.BAD_REQUEST);
		}
		movie.createdNow();

		return new ResponseEntity<>(new ResponseObject<>("Success",  movieRepo.save(movie)), HttpStatus.CREATED);
	}


	@PutMapping("{id}")
	public ResponseEntity<ResponseObject<?>> updateMovie(@PathVariable int id, @RequestBody Movie movie){
		// check if valid id
		Movie movieToUpdate;
		try {
			movieToUpdate = movieRepo.findById(id)
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "couldn't find by id"));

		} catch (ResponseStatusException e) {
			return new ResponseEntity<>(new ResponseObject<>("Failed", errorMessage), HttpStatus.NOT_FOUND);
		}


		// check if valid movie
		try {
			checkIfValidMovie(movie);
		} catch (Exception e) {
			return new ResponseEntity<>(new ResponseObject<>("Failed"), HttpStatus.BAD_REQUEST);
		}

		// update
		movieToUpdate.setTitle(movie.getTitle());
		movieToUpdate.setDescription(movie.getDescription());
		movieToUpdate.setRating(movie.getRating());
		movieToUpdate.setRuntimeMins(movie.getRuntimeMins());
		movieToUpdate.updatedNow();

		return new ResponseEntity<>(new ResponseObject<>("Success", movieRepo.save(movieToUpdate)), HttpStatus.CREATED);
	}

	public void checkIfValidMovie(Movie movie){
		try{
			if (movie.getTitle() == null ||
					movie.getRating() == null ||
					movie.getDescription() == null ||
					movie.getRuntimeMins() == -1 // unsure if i can check runtime mins

			) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid request body");
			}
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid request body");
		}
	}

	@DeleteMapping("{id}")
	public ResponseEntity<ResponseObject<?>> deleteMovie(@PathVariable int id){
		boolean isFound = movieRepo.existsById(id);
		if (! isFound){
			return new ResponseEntity<>(new ResponseObject<>("Failed", errorMessage), HttpStatus.NOT_FOUND);
		}

		Movie delMovie = movieRepo.findById(id).get();
		movieRepo.delete(delMovie);
		return new ResponseEntity<>(new ResponseObject<>("Success", delMovie), HttpStatus.OK);
	}

	@GetMapping("{id}/screenings")
	public ResponseEntity<ResponseObject<?>> getAllScreenings(@PathVariable int id){
		try {
			Movie m = movieRepo.findById(id)
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "couldn't find by id"));
			return new ResponseEntity<>(new ResponseObject<>("Success", m.getScreenings()), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ResponseObject<>("Failed", errorMessage), HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("{id}/screenings")
	public ResponseEntity<ResponseObject<?>> postScreening(@PathVariable int id, @Validated @RequestBody Screening screening){
		Movie m;
		try {
			// check if movie exists
			m = movieRepo.findById(id)
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "couldn't find by id"));
		} catch (ResponseStatusException e){
			return new ResponseEntity<>(new ResponseObject<>("Failed", errorMessage), HttpStatus.NOT_FOUND);
		}
		// check if valid screening
		try{
			if (screening.getScreenNumber() == -1 ||
				screening.getCapacity() == -1 ||
				screening.getStartsAt() == null
			){
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid request body");
			}
		} catch (Exception e) {
			return new ResponseEntity<>(new ResponseObject<>("Failed", errorMessage), HttpStatus.BAD_REQUEST);

		}

		screening.setMovie(m);
		screening.createdNow();
		screeningRepo.save(screening);


		return new ResponseEntity<>(new ResponseObject<>("Success", screening), HttpStatus.CREATED);
	}


}
