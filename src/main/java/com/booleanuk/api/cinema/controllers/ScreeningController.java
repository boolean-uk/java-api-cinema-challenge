package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.Dtos.screenings.ScreeningDto;
import com.booleanuk.api.cinema.Dtos.screenings.ScreeningNew;
import com.booleanuk.api.cinema.repositories.ScreeningRepo;
import com.booleanuk.api.cinema.services.screening.ScreeningServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("movies/{id}/screenings")
public class ScreeningController {

    @Autowired
    ScreeningServiceInterface screeningService;


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ScreeningDto> getAllScreeningForAMovie(@PathVariable Integer id) {
        return screeningService.generateList(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ScreeningDto createScreeningForAMovie(@PathVariable Integer id,@RequestBody ScreeningNew screening){
        return screeningService.generateScreening(id,screening);
    }
}
