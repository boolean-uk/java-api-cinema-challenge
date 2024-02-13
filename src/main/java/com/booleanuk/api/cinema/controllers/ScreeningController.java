package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Movie;
import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.repositories.MovieRepository;
import com.booleanuk.api.cinema.repositories.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("movies/{movieID}/screenings")
public class ScreeningController {
    @Autowired
    protected ScreeningRepository repository;

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping
    public List<Screening> getAll(@PathVariable final Integer movieID) {
        return getMovie(movieID).getScreenings();
    }

    // i thought i needed this function... lmao
    @GetMapping("{id}")
    public ResponseEntity<Screening> getById(@PathVariable final Integer movieID, @PathVariable final Integer id) {
        return new ResponseEntity<>(getScreening(movieID, id), HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Screening> create(@PathVariable final Integer movieID, @RequestBody Screening request) {
        if (request.haveNullFields()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        final Movie _movie = getMovie(movieID);
        _movie.getScreenings().add(request); // add it to the movie as well
        request.setMovie_id(_movie);
        return new ResponseEntity<>(repository.save(request), HttpStatus.CREATED);
    }

    // i thought i needed this function... lmao
    @PutMapping("{id}")
    public ResponseEntity<Screening> update(@PathVariable final Integer movieID, @PathVariable final Integer id, @RequestBody final Screening model) {
        if (model.haveNullFields()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        final Screening _screening = getScreening(movieID, id);
        _screening.copyOverData(model);
        return new ResponseEntity<>(repository.save(_screening), HttpStatus.CREATED);
    }

    // i thought i needed this function... lmao
    @DeleteMapping("{id}")
    public ResponseEntity<Screening> remove(@PathVariable final Integer movieID, @PathVariable final Integer id) {
        final Screening _screening = getScreening(movieID, id);
        repository.delete(_screening);
        return new ResponseEntity<>(_screening, HttpStatus.OK);
    }

    private Movie getMovie(final Integer id) {
        return movieRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No movie with that ID was found."));
    }

    private Screening getScreening(final Integer movieID, final Integer id) {
        Screening _screening = null;
        for (Screening screening : getMovie(movieID).getScreenings()) {
            if (screening.getId().equals(id)) {
                _screening = screening;
                break;
            }
        }
        if (_screening == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No screening ID was found on that movie");
        return _screening;
    }
}
