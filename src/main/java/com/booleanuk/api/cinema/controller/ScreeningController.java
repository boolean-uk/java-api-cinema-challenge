package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.model.Ticket;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.repository.TicketRepository;
import com.booleanuk.api.cinema.response.ErrorResponse;
import com.booleanuk.api.cinema.response.Response;
import com.booleanuk.api.cinema.response.SuccessResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class ScreeningController {

    @Autowired
    private ScreeningRepository screeningRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping("/movies/{id}/screenings")
    public ResponseEntity<Response<?>> getAllScreenings(@PathVariable int id) {
        Movie tempMovie = this.movieRepository.findById(id).orElse(null);
        if(tempMovie == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("not found"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>(tempMovie.getScreenings()));
    }

    @PostMapping("/movies/{id}/screenings")
    public ResponseEntity<Response<?>> createScreening(@PathVariable int id, @RequestBody Screening screening) {
        Movie tempMovie = this.movieRepository.findById(id).orElse(null);
        if(tempMovie == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("not found"));
        }
        screening.setMovie(tempMovie);
        if(containsNull(screening)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("bad request"));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>(screeningRepository.save(screening)));
    }

    @DeleteMapping("/screenings/{id}")
    public ResponseEntity<Response<?>> deleteScreening(@PathVariable int id) {
        Screening screening = screeningRepository.findById(id).orElse(null);

        if(screening == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("not found"));
        }

        if(!screening.getTickets().isEmpty()) {
            for(Ticket ticket: screening.getTickets()) {
                ticketRepository.delete(ticket);
            }
        }

        screeningRepository.delete(screening);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>(screening));
    }

    @PutMapping("/screenings/{id}")
    public ResponseEntity<Response<?>> updateScreening(@PathVariable int id, @RequestBody Screening screening) {
        Screening screeningToUpdate = screeningRepository.findById(id).orElse(null);

        if(screeningToUpdate == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("not found"));
        }

        if(screening.getCapacity() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("bad request"));
        }

        if(screening.getCapacity() < screeningToUpdate.getCapacity()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("the capacity cannot be decreased"));
        }

        screeningToUpdate.setCapacity(screening.getCapacity());

        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse<>(screeningRepository.save(screeningToUpdate)));

    }

    private boolean containsNull(Screening screening) {
        return screening.getScreenNumber() == null || screening.getStartsAt() == null || screening.getCapacity() == null || screening.getMovie() == null;
    }
}


