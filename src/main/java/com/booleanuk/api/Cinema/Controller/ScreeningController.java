package com.booleanuk.api.Cinema.Controller;

import com.booleanuk.api.Cinema.Model.Movie;
import com.booleanuk.api.Cinema.Model.Screening;
import com.booleanuk.api.Cinema.Repository.MovieRepository;
import com.booleanuk.api.Cinema.Repository.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping
public class ScreeningController {

    @Autowired
    private ScreeningRepository screeningRepository;

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping("/movies/{id}/screenings")
    public List<Screening> getAllScreenings(@PathVariable int id){
        return this.screeningRepository.findByMovieId(id);
    }

    @PostMapping("movies/{id}/screenings")
    public ResponseEntity<Screening> createScreening(@PathVariable int id, @RequestBody Screening screening) {
        Movie movie = this.movieRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "This movie id is not in the system"));
        screening.setMovie(movie);
        return new ResponseEntity<Screening>(this.screeningRepository.save(screening), HttpStatus.CREATED);
    }

}
