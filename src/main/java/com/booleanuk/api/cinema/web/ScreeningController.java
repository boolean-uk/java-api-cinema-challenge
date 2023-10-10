package com.booleanuk.api.cinema.web;

import com.booleanuk.api.cinema.domain.dtos.CreateScreeningRequestDTO;
import com.booleanuk.api.cinema.domain.dtos.CustomerResponseDTO;
import com.booleanuk.api.cinema.domain.dtos.ScreeningResponseDTO;
import com.booleanuk.api.cinema.domain.dtos.UpdateScreeningRequestDTO;
import com.booleanuk.api.cinema.responses.CustomerResponse;
import com.booleanuk.api.cinema.responses.ErrorResponse;
import com.booleanuk.api.cinema.responses.Response;
import com.booleanuk.api.cinema.responses.ScreeningResponse;
import com.booleanuk.api.cinema.services.ScreeningService;
import com.booleanuk.api.cinema.utils.ErrorConstants;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/movies/{movieId}/screenings")
public class ScreeningController {
    private final ScreeningService screeningService;

    @Autowired
    public ScreeningController(ScreeningService screeningService) {
        this.screeningService = screeningService;
    }


    @GetMapping
    public ResponseEntity<List<ScreeningResponseDTO>> getScreeningsByMovieId(@PathVariable Long movieId) {
        List<ScreeningResponseDTO> screenings = screeningService.getScreeningsByMovieId(movieId);
        return ResponseEntity.ok(screenings);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getScreeningById(@PathVariable Long id) {
        ScreeningResponseDTO screening = screeningService.getScreeningById(id);
        return getResponseEntity(screening);
    }

    @PostMapping
    public ResponseEntity<Response<?>> createScreening(
            @PathVariable Long movieId,
            @RequestBody @Valid CreateScreeningRequestDTO screeningDTO,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            ErrorResponse error = new ErrorResponse();
            error.set(errorMessages);
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        ScreeningResponseDTO createdScreening = screeningService.createScreening(movieId, screeningDTO);
        ScreeningResponse screeningResponse = new ScreeningResponse();
        screeningResponse.set(createdScreening);
        return new ResponseEntity<>(screeningResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateScreening(
            @PathVariable Long id,
            @RequestBody @Valid UpdateScreeningRequestDTO screeningDTO,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.toList());
            ErrorResponse error = new ErrorResponse();
            error.set(errorMessages);
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }

        ScreeningResponseDTO updatedScreening = screeningService.updateScreening(id, screeningDTO);
        ScreeningResponse screeningResponse = new ScreeningResponse();
        screeningResponse.set(updatedScreening);
        return new ResponseEntity<>(screeningResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteScreening(@PathVariable Long id) {
        ScreeningResponseDTO deletedScreening = screeningService.deleteScreening(id);
        return getResponseEntity(deletedScreening);
    }

    private ResponseEntity<Response<?>> getResponseEntity(ScreeningResponseDTO screening) {
        if (screening == null) {
            ErrorResponse error = new ErrorResponse();
            error.set(ErrorConstants.SCREENING_NOT_FOUND_MESSAGE);
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        ScreeningResponse screeningResponse = new ScreeningResponse();
        screeningResponse.set(screening);
        return ResponseEntity.ok(screeningResponse);
    }
}