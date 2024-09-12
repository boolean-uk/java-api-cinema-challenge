package com.booleanuk.api.cinema.screening.controller;

import com.booleanuk.api.cinema.response.ResponseInterface;
import com.booleanuk.api.cinema.screening.model.Screening;
import com.booleanuk.api.cinema.screening.model.ScreeningDTO;
import com.booleanuk.api.cinema.screening.repository.ScreeningRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import static com.booleanuk.api.cinema.response.ResponseFactory.*;



@RestController
@RequestMapping("/screenings")
public class ScreeningController {
    @Autowired
    ScreeningRepository screeningRepository;

    @PostMapping
    public ResponseEntity<ResponseInterface> addScreening(@RequestBody ScreeningDTO screeningDTO) throws ResponseStatusException {
        try {
            Screening screening = convertFromDTO(screeningDTO);
            return CreatedSuccessResponse(screening);
        } catch (Exception e) {
            return BadRequestErrorResponse();
        }
    }

    @GetMapping
    public ResponseEntity<ResponseInterface> getAllScreenings() throws ResponseStatusException {
        return OkSuccessResponse(this.screeningRepository.findAll());
    }

    private Screening convertFromDTO(ScreeningDTO screeningDTO) {
        String formattedDate = screeningDTO.getStartsAt().replace(" ", "T");
        OffsetDateTime startsAt = OffsetDateTime.parse(formattedDate, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        return new Screening(screeningDTO.getScreenNumber(), screeningDTO.getCapacity(), startsAt);
    }
}
