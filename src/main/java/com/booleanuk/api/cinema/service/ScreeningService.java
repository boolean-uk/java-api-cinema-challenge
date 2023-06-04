package com.booleanuk.api.cinema.service;

import com.booleanuk.api.cinema.model.Screening;

import java.util.List;

public interface ScreeningService {
    List<Screening> getScreenings();
    Screening getScreening(Long id);
    Screening createScreening(Screening screening);
    Screening updateScreening(Long id, Screening screening);
    Screening deleteScreening(Long id);
}
