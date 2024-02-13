package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.dto.ScreeningDto;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.response.ApiException;
import com.booleanuk.api.cinema.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("movies/{movId}/screenings")
public class ScreeningController {
    @Autowired
    ScreeningRepository repository;

    @Autowired
    MovieRepository movieRepository;

    @GetMapping
    public ResponseEntity<Response<List<ScreeningDto>>> getAllScreeningOffMovie(@PathVariable int movId) {
        List<Screening> screenings = this.movieRepository.findById(movId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "not found")).getScreenings();
        if (screenings.isEmpty()) {
            throw new ApiException(HttpStatus.NOT_FOUND, "No screenings of this movie");
        }
        return ResponseEntity.ok(new Response<>(this.repository.findByMovieId(movId)));
    }

    @PostMapping
    public ResponseEntity<Response<ScreeningDto>> createScreening(@PathVariable int movId, @RequestBody Screening screening) {
        if (screening.getScreenNumber() <= 0 || screening.getCapacity() <= 0 || screening.getStartsAt() == null) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "bad request");
        }
        screening.setMovie(this.movieRepository.findById(movId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "not found")));
        Screening createdScreening = this.repository.save(screening);
        createdScreening.setTickets(new ArrayList<>());
        return new ResponseEntity<>(new Response<>(this.translateToDto(createdScreening)), HttpStatus.CREATED);
    }

    public ScreeningDto translateToDto(Screening screening) {
        return new ScreeningDto(screening.getId(), screening.getScreenNumber(), screening.getCapacity(), screening.getStartsAt(), screening.getCreatedAt(), screening.getUpdatedAt());
    }
}
