package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("screenings")
public class ScreeningController {
    @Autowired
    private ScreeningRepository repository;
    //i made this in case i want to see all screenings for all movies
    @GetMapping
    public List<Screening> getAll(){
        return this.repository.findAll();
    }
}
