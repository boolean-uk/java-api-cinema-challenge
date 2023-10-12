package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;


@RestController
@RequestMapping("/movies/{id}/screenings")
public class ScreeningController {

    @Autowired
    private final ScreeningRepository screeningRepository;

    public ScreeningController(ScreeningRepository repository) {
        this.screeningRepository = repository;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Screening customerCreated(@RequestBody Screening newScreening) throws SQLException {


        //////////////////////////////////////////////////////////////////////////
        //Set date for new screening
        OffsetDateTime now = OffsetDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        String timestamp = now.format(format);
        newScreening.setCreatedAt(timestamp);
        newScreening.setUpdatedAt(timestamp);
        //////////////////////////////////////////////////////////////////////////

        newScreening.setStartsAt(newScreening.getStartsAt().replace(" ", "T"));
        return this.screeningRepository.save(newScreening);
    }

    @GetMapping
    public Screening getScreeningById(@PathVariable int id) {
        Screening screening = null;
        screening = this.screeningRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Screening was not found")
        );
        return screening;
    }


}
