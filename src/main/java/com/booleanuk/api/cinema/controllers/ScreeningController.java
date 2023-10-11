package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Movie;
import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.models.DTOs.ScreeningNoRelationsDTO;
import com.booleanuk.api.cinema.repositories.ScreeningRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("movies/{movieId}/screenings")
public class ScreeningController {
    private record ScreeningSingleDTO (String status, ScreeningNoRelationsDTO data) {}
    private record ScreeningListDTO (String status, List<ScreeningNoRelationsDTO> data) {}
    @Autowired
    private ScreeningRepository screeningRepository;
    @Autowired
    private ModelMapper modelMapper;

    // ------------------ ENDPOINTS ------------------//
    //region // POST //
    @PostMapping
    public ResponseEntity<ScreeningSingleDTO> create(@RequestBody Screening screening, @PathVariable int movieId) {
        Screening screeningToCreate = screening;
        screeningToCreate.setMovie(new Movie(movieId));

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ScreeningSingleDTO("success", modelMapper
                        .map(this.screeningRepository.save(screeningToCreate), ScreeningNoRelationsDTO.class)
                ));
    }
    //endregion
    //region // GET //
    @GetMapping
    public ResponseEntity<ScreeningListDTO> getAll(@PathVariable int movieId) {
        return ResponseEntity
                .ok(new ScreeningListDTO("success", this.screeningRepository.findAll().stream()
                        .filter(x -> x.getMovie().getId() == movieId)
                        .map(x -> modelMapper
                                .map(x, ScreeningNoRelationsDTO.class))
                        .collect(Collectors.toList())
                ));
    }
    //endregion
}