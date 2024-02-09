package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.dto.MovieDto;
import com.booleanuk.api.cinema.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("movies")
public class MovieController {
    @Autowired
    MovieRepository repository;

    @GetMapping
    public List<MovieDto> getAllMovies() {
        return this.repository.findAllProjectedBy();
    }
}
