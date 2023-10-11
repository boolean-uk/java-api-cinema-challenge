package com.booleanuk.api.cinema.services;

import com.booleanuk.api.cinema.domain.dtos.CreateScreeningRequestDTO;
import com.booleanuk.api.cinema.domain.dtos.ScreeningResponseDTO;
import com.booleanuk.api.cinema.domain.dtos.UpdateScreeningRequestDTO;

import java.util.List;

public interface ScreeningService {
    ScreeningResponseDTO createScreening(Long movieId, CreateScreeningRequestDTO screeningDTO);

    List<ScreeningResponseDTO> getScreeningsByMovieId(Long movieId);

    ScreeningResponseDTO getScreeningById(Long screeningId);

    ScreeningResponseDTO updateScreening(Long screeningId, UpdateScreeningRequestDTO screeningDTO);

    ScreeningResponseDTO deleteScreening(Long screeningId);
}