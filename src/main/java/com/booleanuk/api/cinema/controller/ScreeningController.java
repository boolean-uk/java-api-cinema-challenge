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
    private ScreeningRepository screeningRepository;
    @GetMapping
    public List<Screening> getAllScreenings(){
        return this.screeningRepository.findAll();
    }

}
