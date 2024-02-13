package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.HelperUtils;
import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import com.booleanuk.api.cinema.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("screenings")
public class ScreeningController {
    @Autowired
    private ScreeningRepository screeningRepository;
    @Autowired
    private MovieRepository movieRepository;

    @GetMapping("/all")
    public List<Screening> getAllScreenings() {
        return this.screeningRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Screening>> createScreening(@RequestBody Screening screening) {
        try {
            Date date = new Date();
            screening.setCreatedAt(date);
            screening.setUpdatedAt(date);
            if (HelperUtils.invalidScreeningFields(screening)) {
                ApiResponse<Screening> badRequest = new ApiResponse<>("error", new ApiResponse.Message("bad request"));
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequest);
            } else {
                Movie tempMovie = getAMovie(screening.getMovie().getId());
                screening.setMovie(tempMovie);
                Screening savedScreening = this.screeningRepository.save(screening);
                ApiResponse<Screening> createdRequest = new ApiResponse<>("success", savedScreening);
                return ResponseEntity.status(HttpStatus.CREATED).body(createdRequest);
            }
        } catch (Exception e) {
            ApiResponse<Screening> badRequest = new ApiResponse<>("error", new ApiResponse.Message("bad request"));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequest);
        }
    }

    /**
     * Local Helper method(s)
     * @param id
     * @return
     */
    private Movie getAMovie(int id) {
        return this.movieRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found"));
    }
}
