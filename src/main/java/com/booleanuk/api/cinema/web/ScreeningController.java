package com.booleanuk.api.cinema.web;

import com.booleanuk.api.cinema.domain.dtos.CreateScreeningRequestDTO;
import com.booleanuk.api.cinema.domain.dtos.ScreeningResponseDTO;
import com.booleanuk.api.cinema.domain.dtos.UpdateScreeningRequestDTO;
import com.booleanuk.api.cinema.services.ScreeningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies/{movieId}/screenings")
public class ScreeningController {
    private final ScreeningService screeningService;

    @Autowired
    public ScreeningController(ScreeningService screeningService) {
        this.screeningService = screeningService;
    }

    @PostMapping
    public ScreeningResponseDTO createScreening(
            @PathVariable Long movieId,
            @RequestBody CreateScreeningRequestDTO screeningDTO
    ) {
        return screeningService.createScreening(movieId, screeningDTO);
    }

    @GetMapping
    public List<ScreeningResponseDTO> getScreeningsByMovieId(@PathVariable Long movieId) {
        return screeningService.getScreeningsByMovieId(movieId);
    }

    @GetMapping("/{id}")
    public ScreeningResponseDTO getScreeningById(@PathVariable Long id) {
        return screeningService.getScreeningById(id);
    }

    @PutMapping("/{id}")
    public ScreeningResponseDTO updateScreening(
            @PathVariable Long id,
            @RequestBody UpdateScreeningRequestDTO screeningDTO
    ) {
        return screeningService.updateScreening(id, screeningDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteScreening(@PathVariable Long id) {
        screeningService.deleteScreening(id);
    }
}