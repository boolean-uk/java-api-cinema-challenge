package com.booleanuk.api.cinema.movies;

import com.booleanuk.api.cinema.movies.screenings.Screening;
import com.booleanuk.api.cinema.movies.screenings.ScreeningRepository;
import com.booleanuk.api.generic.GenericController;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("movies")
public class MovieController extends GenericController<Movie> {
  private final ScreeningRepository screeningRepository;

  public MovieController(MovieRepository movieRepository, ScreeningRepository screeningRepository) {
    super(movieRepository);
    this.screeningRepository = screeningRepository;
  }

  @Override
  public ResponseEntity<Movie> post(@RequestBody Movie movie) {
    try {
      movie.getScreenings().forEach(screening -> {
        screening.setMovie(movie);
        this.screeningRepository.save(screening);
      });

      return ResponseEntity.status(HttpStatus.CREATED).body(this.repository.save(movie));
    } catch (DataIntegrityViolationException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
  }

  @Override
  public ResponseEntity<Movie> put(@PathVariable int id, @RequestBody Movie movie) {
    return this.repository.findById(id).map(existing -> {
      existing.update(movie);
      existing.setUpdatedAt(LocalDateTime.now().toString());
      return ResponseEntity.status(HttpStatus.CREATED).body(this.repository.save(existing));
    }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No movie with id '" + id + "' was found"));
  }

  @PostMapping(value = "{id}/screenings")
  public ResponseEntity<Screening> postScreening(@PathVariable int id, @RequestBody Screening screening) {
    try {
      var movie = this.repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
      screening.setMovie(movie);

      return ResponseEntity.status(HttpStatus.CREATED).body(this.screeningRepository.save(screening));
    } catch (DataIntegrityViolationException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    }
  }

  @GetMapping(value = "{id}/screenings")
  public ResponseEntity<List<Screening>> getScreenings(@PathVariable int id) {
    var movie = this.repository.findById(id);
    if (movie.isEmpty())
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);

    return ResponseEntity.ok(this.screeningRepository.findAll().stream()
        .filter(screening -> screening.getMovie().getId() == id)
        .toList());
  }
}