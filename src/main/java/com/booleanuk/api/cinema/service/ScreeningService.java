package com.booleanuk.api.cinema.service;

import com.booleanuk.api.cinema.model.Screening;

import java.util.List;

public interface ScreeningService {
    List<Screening> getScreenings(long id);
    Screening getScreening(long id);
    Screening createScreening(long id, Screening screening);
    Screening updateScreening(long id, Screening screening);
    Screening deleteScreening(long id);
}
