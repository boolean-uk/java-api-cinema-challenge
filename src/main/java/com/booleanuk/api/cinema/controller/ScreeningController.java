package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.repository.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("screenings")
public class ScreeningController {
    @Autowired
    ScreeningRepository repository;
}
