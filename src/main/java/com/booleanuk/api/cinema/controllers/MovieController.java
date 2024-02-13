package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Movie;
import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.repositories.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("movies")
public class MovieController extends ControllerTemplate<Movie> {
    @Autowired
    private ScreeningRepository screeningRepository;

    @Override
    public ResponseEntity<Movie> create(@RequestBody Movie request) {
        if (request.haveNullFields()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        final var _response = new ResponseEntity<>(repository.save(request), HttpStatus.CREATED);

        if (request.getScreenings() != null) {
            for (Screening screening : request.getScreenings()) {
                if (request.haveNullFields()) continue;
                screening.setMovie_id(request); // repository.getReferenceById(request.getId())
                screeningRepository.save(screening);
            }
        }

        return _response;
    }
}
