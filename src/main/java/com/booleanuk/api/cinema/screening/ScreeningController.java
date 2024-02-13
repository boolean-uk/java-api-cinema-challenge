package com.booleanuk.api.cinema.screening;

import com.booleanuk.api.cinema.movie.Movie;
import com.booleanuk.api.cinema.movie.MovieRepository;
import com.booleanuk.api.cinema.responses.ErrorResponse;
import com.booleanuk.api.cinema.responses.Response;
import com.booleanuk.api.cinema.ticket.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("movies/{id}/screenings")
public class ScreeningController {
    @Autowired
    private ScreeningRepository repo;

    @Autowired
    private MovieRepository movies;

    @PostMapping
    public ResponseEntity<Response<?>> create(@PathVariable int id, @RequestBody Screening screening){
        if (screening.getScreenNumber() <= 0 ||
                screening.getCapacity() <= 0 ||
                screening.getStartsAt() == null){
            ErrorResponse badRequest = new ErrorResponse();
            badRequest.set("bad request");

            return new ResponseEntity<>(badRequest, HttpStatus.BAD_REQUEST);
        }

        Movie tempMovie = movies
                .findById(id)
                .orElse(null);

        if (tempMovie == null){
            ErrorResponse notFound = new ErrorResponse();
            notFound.set("not found");

            return new ResponseEntity<>(notFound, HttpStatus.NOT_FOUND);
        }

        screening.setMovie(tempMovie);
        screening.setCreatedAt(nowFormatted());
        screening.setUpdatedAt(nowFormatted());
        screening.setTickets(new ArrayList<Ticket>());
        repo.save(screening);

        Response<Screening> screeningResponse = new Response<>();
        screeningResponse.set(screening);

        return new ResponseEntity<>(screeningResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Response<?>> getScreeningsForMovie(@PathVariable int id) {
        Movie movie = movies
                .findById(id)
                .orElse(null);

        if (movie == null){
            ErrorResponse notFound = new ErrorResponse();
            notFound.set("not found");

            return new ResponseEntity<>(notFound, HttpStatus.NOT_FOUND);
        }

        List<Screening> screenings = movie.getScreenings();
        Response<List<Screening>> screeningListResponse = new Response<>();
        screeningListResponse.set(screenings);

        return ResponseEntity.ok(screeningListResponse);
    }

    private String nowFormatted(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return now.format(format);
    }
}
