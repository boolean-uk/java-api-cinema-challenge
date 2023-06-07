package com.booleanuk.api.cinema.services.screening;

import com.booleanuk.api.cinema.Dtos.screenings.ScreeningDto;
import com.booleanuk.api.cinema.Dtos.screenings.ScreeningNew;

import java.util.List;



public interface ScreeningServiceInterface {
    List<ScreeningDto> generateList(int id);

    ScreeningDto generateScreening(int id, ScreeningNew screeningNew);
}
