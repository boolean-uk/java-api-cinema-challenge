package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.dto.ScreeningDto;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("movies/{movId}/screenings")
public class ScreeningController {
    @Autowired
    ScreeningRepository repository;

    @GetMapping
    public List<ScreeningDto> getAllScreeningOffMovie(@PathVariable int movId) {
        return this.repository.findByMovieId(movId);
    }
}
