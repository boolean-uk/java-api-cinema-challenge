package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.model.Ticket;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.repository.TicketRepository;
import com.booleanuk.api.cinema.response.BadRequestResponse;
import com.booleanuk.api.cinema.response.NotFoundResponse;
import com.booleanuk.api.cinema.response.Response;
import com.booleanuk.api.cinema.response.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("movies")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ScreeningRepository screeningRepository;
    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping
    public ResponseEntity<Response> getAllMovies() {
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse(this.movieRepository.findAll()));

    }

    @PostMapping
    public ResponseEntity<Response> createMovie(@RequestBody Movie movie) {
        if(containsNull(movie)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BadRequestResponse());
        }

        // Set screenings
        if(movie.getScreenings() != null) {
            List<Screening> screenings = new ArrayList<>();

            for(Screening screening: movie.getScreenings()) {
                if(screening.getScreenNumber() == null || screening.getStartsAt() == null || screening.getCapacity() == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BadRequestResponse());
                }
                screening.setMovie(movie);
                screenings.add(screening);
            }
            movie.setScreenings(screenings);
            Movie data = movieRepository.save(movie);
            screeningRepository.saveAll(screenings);
            Response response = new SuccessResponse(data);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        Response response = new SuccessResponse(movieRepository.save(movie));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteMovie(@PathVariable int id) {
        Movie movieToDelete = findMovie(id);

        if(movieToDelete == null) {
            Response response = new NotFoundResponse();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        // Delete screenings of the movie
        for(Screening screening: movieToDelete.getScreenings()) {
            //Delete tickets of screening
            for(Ticket ticket: screening.getTickets()) {
                ticketRepository.delete(ticket);
            }
            screeningRepository.delete(screening);
        }

        movieRepository.delete(movieToDelete);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse(movieToDelete));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateMovie(@PathVariable int id, @RequestBody Movie movie) {
        Movie movieToUpdate = findMovie(id);
        if(movieToUpdate == null) {
            Response response = new NotFoundResponse();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        if(movie.getTitle() != null ) {
            movieToUpdate.setTitle(movie.getTitle());
        }
        if(movie.getRating() != null ) {
            movieToUpdate.setRating(movie.getRating());
        }
        if(movie.getDescription() != null ) {
            movieToUpdate.setDescription(movie.getDescription());
        }
        if(movie.getRuntimeMins() != null) {
            movieToUpdate.setRuntimeMins(movie.getRuntimeMins());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse(movieRepository.save(movieToUpdate)));

    }

    private Movie findMovie(int id) {
        return movieRepository.findById(id).orElse(null);
    }

    private boolean containsNull(Movie movie) {
        return movie.getTitle() == null || movie.getRating() == null || movie.getDescription() == null || movie.getRuntimeMins() == null;
    }
}


