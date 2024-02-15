package com.booleanuk.api.controller;

import com.booleanuk.api.model.Movie;
import com.booleanuk.api.model.Screening;
import com.booleanuk.api.model.Ticket;
import com.booleanuk.api.repository.MovieRepository;
import com.booleanuk.api.repository.ScreeningRepository;
import com.booleanuk.api.repository.TicketRepository;
import com.booleanuk.api.response.BadRequestResponse;
import com.booleanuk.api.response.NotFoundResponse;
import com.booleanuk.api.response.ResponseTemplate;
import com.booleanuk.api.response.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("movies")
public class MovieController {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private ScreeningRepository screeningRepository;

    @GetMapping
    public ResponseEntity<ResponseTemplate>  getAllMovies() {
        return new ResponseEntity<>(new SuccessResponse(this.movieRepository.findAll()), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseTemplate> createMovie(@RequestBody Movie movie) {
        if (areAnyFieldsBad(movie)) {
            return new ResponseEntity<>(new BadRequestResponse(), HttpStatus.BAD_REQUEST);
        }
        movie.setScreenings(new ArrayList<>());
        this.movieRepository.save(movie);
        return new ResponseEntity<>(new SuccessResponse(movie), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseTemplate> deleteMovie(@PathVariable int id) {
        if (doesMovieIDNotExist(id)) {
            return new ResponseEntity<>(new NotFoundResponse(), HttpStatus.NOT_FOUND);
        }
        Movie movieToDelete = this.getMovieByID(id);
        if (!movieToDelete.getScreenings().isEmpty()) {
            removeRelatedScreeningsAndTickets(movieToDelete);
        }
        this.movieRepository.delete(movieToDelete);
        movieToDelete.setScreenings(new ArrayList<>());
        return new ResponseEntity<>(new SuccessResponse(movieToDelete), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseTemplate> updateMovie(@PathVariable int id, @RequestBody Movie movie) {
        if (movie == null || (movie.getTitle() == null && movie.getRating() == null
                && movie.getDescription() == null && movie.getRunTimeMins() == 0)) {
            return new ResponseEntity<>(new BadRequestResponse(), HttpStatus.BAD_REQUEST);
        }
        if (doesMovieIDNotExist(id)) {
            return new ResponseEntity<>(new NotFoundResponse(), HttpStatus.NOT_FOUND);
        }
        Movie movieToUpdate = this.getMovieByID(id);
        if (movie.getTitle() != null && !movie.getTitle().isBlank()) {
            movieToUpdate.setTitle(movie.getTitle());
        }
        if (movie.getRating() != null && !movie.getRating().isBlank()) {
            movieToUpdate.setRating(movie.getRating());
        }
        if (movie.getDescription() != null && !movie.getDescription().isBlank()) {
            movieToUpdate.setDescription(movie.getDescription());
        }
        if (movie.getRunTimeMins() != 0) {
            movieToUpdate.setRunTimeMins(movie.getRunTimeMins());
        }
        this.movieRepository.save(movieToUpdate);
        return new ResponseEntity<>(new SuccessResponse(movieToUpdate), HttpStatus.CREATED);
    }

    //--------------------------- Private section---------------------------//

    private void removeRelatedScreeningsAndTickets(Movie movie) {
        for (Screening screening : movie.getScreenings()) {
            for (Ticket ticket : screening.getTickets()) {
                this.ticketRepository.delete(ticket);
            }
            this.screeningRepository.delete(screening);
        }
    }

    private Movie getMovieByID(int id) {
        for (Movie movie : this.movieRepository.findAll()) {
            if (movie.getId() == id) {
                return movie;
            }
        }
        return new Movie();
    }

    private boolean doesMovieIDNotExist(int id) {
        for (Movie movie : this.movieRepository.findAll()) {
            if (movie.getId() == id) {
                return false;
            }
        }
        return true;
    }

    private boolean areAnyFieldsBad(Movie movie) {
        if (movie.getTitle() == null ||
                movie.getRating() == null ||
                movie.getDescription() == null ||
                movie.getRunTimeMins() == 0 ||
                movie.getTitle().isBlank() ||
                movie.getRating().isBlank() ||
                movie.getDescription().isBlank())
        {
            return true;
        }
        return false;
    }
}
