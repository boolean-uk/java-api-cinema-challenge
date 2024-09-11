package com.booleanuk.api.cinema.controller;


import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.view.MovieRepository;
import com.booleanuk.api.cinema.view.ScreeningRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.List;

@RestController
@RequestMapping("movies")
public class MovieController {
	private MovieRepository movieRepo;
	private ScreeningRepository screeningRepo;

	public MovieController(MovieRepository movieRepository, ScreeningRepository screeningRepo){
		this.movieRepo = movieRepository;
		this.screeningRepo = screeningRepo;
	}

	@GetMapping //all
	public ResponseEntity<List<Movie>> getAll(){
		return new ResponseEntity<>(movieRepo.findAll(), HttpStatus.OK);
	}


	@PostMapping
	public ResponseEntity<Movie> postOne(@RequestBody Movie movie){
		checkIfValidMovie(movie);
		// throws error if invalid.

		movie.setCreatedAt(OffsetDateTime.now());

		return new ResponseEntity<>(movieRepo.save(movie), HttpStatus.CREATED);
	}


	@PutMapping("{id}")
	public ResponseEntity<Movie> updateMovie(@PathVariable int id, @RequestBody Movie movie){
		Movie movieToUpdate = movieRepo.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "couldn't find by id" ));

		checkIfValidMovie(movie);
		// throws error if invalid.

		// update
		movieToUpdate.setTitle(movie.getTitle());
		movieToUpdate.setDescription(movie.getDescription());
		movieToUpdate.setRating(movie.getRating());
		movieToUpdate.setRuntimeMins(movie.getRuntimeMins());
		movieToUpdate.setUpdatedAt(OffsetDateTime.now());

		return new ResponseEntity<>(movieRepo.save(movieToUpdate), HttpStatus.CREATED);
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
	public ResponseEntity<Movie> deleteMovie(@PathVariable int id){
		boolean isFound = movieRepo.existsById(id);
		if (! isFound){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		Movie delMovie = movieRepo.findById(id).get();
		movieRepo.delete(delMovie);
		return new ResponseEntity<>(delMovie, HttpStatus.OK);
	}

	@GetMapping("{id}/screenings")
	public ResponseEntity<List<Screening>> getAllScreenings(@PathVariable int id){
		Movie m = movieRepo.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "couldn't find by id" ));
		return new ResponseEntity<>(m.getScreenings(), HttpStatus.OK);
	}

	@PostMapping("{id}/screenings")
	public ResponseEntity<Screening> postScreening(@PathVariable int id, @RequestBody Screening screening){
		// check if movie exists
		Movie m = movieRepo.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "couldn't find by id" ));

		// check if valid screening
		try{
			if (screening.getScreenNumber() == -1 ||
				screening.getCapacity() == -1 ||
				screening.getStartsAt() == null
			){
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid request body");
			}
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid request body");
		}

		screening.setMovie(m);
		screening.setCreatedAt(OffsetDateTime.now());
		screeningRepo.save(screening);

		return new ResponseEntity<>(screening, HttpStatus.CREATED);
	}


}
