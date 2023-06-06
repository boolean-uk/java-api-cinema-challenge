package com.booleanuk.api.cinema.services;

import com.booleanuk.api.cinema.Dtos.ScreeningDto;
import com.booleanuk.api.cinema.Dtos.ScreeningNew;
import com.booleanuk.api.cinema.entities.Screening;

import java.util.List;



public interface ScreeningServiceInterface {
    List<ScreeningDto> generateList(int id);

    ScreeningDto generateScreening(int id, ScreeningNew screeningNew);
}
