package com.booleanuk.api.cinema.service;

import com.booleanuk.api.cinema.dto.ScreeningDTO;
import com.booleanuk.api.cinema.exceptions.EntityNotFoundException;
import com.booleanuk.api.cinema.model.Screening;

import java.util.List;

public interface IScreeningService {

    List<Screening> getScreenings(int movieId);
    Screening insertScreening(int movieId, ScreeningDTO screeningDTO);
}
