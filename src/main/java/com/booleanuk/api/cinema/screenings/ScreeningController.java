package com.booleanuk.api.cinema.screenings;

import com.booleanuk.api.cinema.movies.Movie;
import com.booleanuk.api.cinema.movies.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("screenings")
public class ScreeningController {
    @Autowired
    private ScreeningRepository screeningRepository;

    @Autowired
    private MovieRepository movieRepository;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{id}")
    public Screening getOneScreening(@PathVariable int id) {
        return this.screeningRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Screening not found.")
        );
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Screening> getAllScreenings() {
        return this.screeningRepository.findAll();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Screening createScreening(@RequestBody Screening screening) {
        Screening newScreening = new Screening(screening.getScreenNumber(), screening.getStartsAt(), screening.getCapacity());
        Movie movieRef = this.movieRepository.findById(screening.getMovie().getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found.")
        );
        newScreening.setMovie(movieRef);
        return this.screeningRepository.save(newScreening);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("{id}")
    public Screening updateScreening(@RequestBody Screening screening, @PathVariable int id) {
        Screening screeningToUpdate = this.screeningRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Screening not found.")
        );
        Movie movieRef = this.movieRepository.findById(screening.getMovie().getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Movie not found.")
        );
        screeningToUpdate.setMovie(movieRef);
        screeningToUpdate.setScreenNumber(screening.getScreenNumber());
        screeningToUpdate.setStartsAt(screening.getStartsAt());
        screeningToUpdate.setCapacity(screening.getCapacity());
        return this.screeningRepository.save(screeningToUpdate);
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("{id}")
    public Screening deleteScreening(@PathVariable int id) {
        Screening screeningToDelete = this.screeningRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Screening not found.")
        );
        this.screeningRepository.delete(screeningToDelete);
        return screeningToDelete;
    }
}