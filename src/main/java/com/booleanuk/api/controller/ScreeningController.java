package com.booleanuk.api.controller;

import com.booleanuk.api.model.Movie;
import com.booleanuk.api.model.Screening;
import com.booleanuk.api.repository.MovieRepository;
import com.booleanuk.api.repository.ScreeningRepository;
import com.booleanuk.api.response.BadRequestResponse;
import com.booleanuk.api.response.NotFoundResponse;
import com.booleanuk.api.response.ResponseTemplate;
import com.booleanuk.api.response.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("movies")
public class ScreeningController {
    @Autowired
    private ScreeningRepository screeningRepository;
    @Autowired
    private MovieRepository movieRepository;

    @GetMapping("/screenings")
    public ResponseEntity<ResponseTemplate> getAllScreenings() {
        List<Screening> allSpecifiedScreenings = this.screeningRepository.findAll();
        return new ResponseEntity<>(new SuccessResponse(allSpecifiedScreenings), HttpStatus.OK);
    }

    @GetMapping("/{movie_id}/screenings")
    public ResponseEntity<ResponseTemplate> getAllScreeningsForMovie(@PathVariable int movie_id) {
        if (this.doesMovieIDNotExist(movie_id)) {
            return new ResponseEntity<>(new NotFoundResponse(), HttpStatus.NOT_FOUND);
        }
        List<Screening> allSpecifiedScreenings = new ArrayList<>();
        for (Screening screening : this.screeningRepository.findAll()) {
            if (screening.getMovie().getId() == movie_id) {
                allSpecifiedScreenings.add(screening);
            }
        }
        return new ResponseEntity<>(new SuccessResponse(allSpecifiedScreenings), HttpStatus.OK);
    }

    @PostMapping("/{movie_id}/screenings")
    public ResponseEntity<ResponseTemplate> createScreening(@PathVariable int movie_id,
                                                     @RequestBody Screening screening) {
        if (doesMovieIDNotExist(movie_id)) {
            return new ResponseEntity<>(new NotFoundResponse(), HttpStatus.NOT_FOUND);
        }
        if (areAnyFieldsBadForCreating(screening)) {
            return new ResponseEntity<>(new BadRequestResponse(), HttpStatus.BAD_REQUEST);
        }
        Movie tempMovie = getMovieByID(movie_id);
        screening.setMovie(tempMovie);
        screening.setTickets(new ArrayList<>());
        this.screeningRepository.save(screening);
        return new ResponseEntity<>(new SuccessResponse(screening), HttpStatus.CREATED);
    }

    //--------------------------- Private section---------------------------//

    private boolean doesMovieIDNotExist(int id) {
        for (Movie movie : this.movieRepository.findAll()) {
            if (movie.getId() == id) {
                return false;
            }
        }
        return true;
    }

    private boolean areAnyFieldsBadForCreating(Screening screening) {
        if (screening.getScreenNumber() == 0 ||
            screening.getCapacity() == 0 ||
            screening.getStartsAt() == null)
        {
            return true;
        }
        return false;
    }

    private Movie getMovieByID(int id) {
        for (Movie movie : this.movieRepository.findAll()) {
            if (movie.getId() == id) {
                return movie;
            }
        }
        return new Movie();
    }
}
