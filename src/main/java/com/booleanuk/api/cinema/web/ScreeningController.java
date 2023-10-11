package com.booleanuk.api.cinema.web;

import com.booleanuk.api.cinema.domain.dtos.CreateScreeningRequestDTO;
import com.booleanuk.api.cinema.domain.dtos.ScreeningResponseDTO;
import com.booleanuk.api.cinema.domain.dtos.UpdateScreeningRequestDTO;
import com.booleanuk.api.cinema.errors.ResourceNotFoundException;
import com.booleanuk.api.cinema.responses.ErrorResponse;
import com.booleanuk.api.cinema.responses.Response;
import com.booleanuk.api.cinema.responses.ScreeningResponse;
import com.booleanuk.api.cinema.services.ScreeningService;
import com.booleanuk.api.cinema.utils.ErrorConstants;
import com.booleanuk.api.cinema.utils.ErrorUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("/movies/{movieId}/screenings")
public class ScreeningController {
    private final ScreeningService screeningService;

    @Autowired
    public ScreeningController(ScreeningService screeningService) {
        this.screeningService = screeningService;
    }


    @GetMapping
    public ResponseEntity<Response<?>> getScreeningsByMovieId(@PathVariable Long movieId) {
        try {
            List<ScreeningResponseDTO> screenings = screeningService.getScreeningsByMovieId(movieId);
            Response<List<ScreeningResponseDTO>> response = new Response<>();
            response.set(screenings);
            return ResponseEntity.ok(response);
        } catch (ResourceNotFoundException e) {
            ErrorResponse error = new ErrorResponse();
            error.set(e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<?>> getOneScreeningById(@PathVariable Long movieId,
                                                           @PathVariable Long id) {
        try {
            ScreeningResponseDTO screening = screeningService.getScreeningById(id);
            if (screening != null && !screening.getMovieId().equals(movieId)) {
                ErrorResponse error = new ErrorResponse();
                error.set(ErrorConstants.SCREENING_NOT_MATCHING_MOVIE_MESSAGE);
                return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
            }
            return getResponseEntity(screening);
        } catch (ResourceNotFoundException e) {
            ErrorResponse error = new ErrorResponse();
            error.set(e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping
    public ResponseEntity<Response<?>> createScreening(
            @PathVariable Long movieId,
            @RequestBody @Valid CreateScreeningRequestDTO screeningDTO,
            BindingResult bindingResult
    ) {
        ResponseEntity<Response<?>> error = ErrorUtil.getErrors(bindingResult);
        if (error != null) return error;
        try {
            ScreeningResponseDTO createdScreening = screeningService.createScreening(movieId, screeningDTO);
            ScreeningResponse screeningResponse = new ScreeningResponse();
            screeningResponse.set(createdScreening);
            return new ResponseEntity<>(screeningResponse, HttpStatus.CREATED);
        } catch (ResourceNotFoundException e) {
            ErrorResponse errorNotFound = new ErrorResponse();
            errorNotFound.set(e.getMessage());
            return new ResponseEntity<>(errorNotFound, HttpStatus.NOT_FOUND);
        } catch (DateTimeParseException ex) {
            ErrorResponse dateError = new ErrorResponse();
            dateError.set(ErrorConstants.DATE_TIME_FORMAT_ERROR_MESSAGE);
            return new ResponseEntity<>(dateError, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<?>> updateScreening(
            @PathVariable Long id,
            @PathVariable Long movieId,
            @RequestBody @Valid UpdateScreeningRequestDTO screeningDTO,
            BindingResult bindingResult
    ) {
        ResponseEntity<Response<?>> error = ErrorUtil.getErrors(bindingResult);
        if (error != null) return error;
        try {
           ScreeningResponseDTO updatedScreening = screeningService.updateScreening(id, screeningDTO);
           if (updatedScreening != null && !updatedScreening.getMovieId().equals(movieId)) {
               ErrorResponse anError = new ErrorResponse();
               anError.set(ErrorConstants.SCREENING_NOT_MATCHING_MOVIE_MESSAGE);
               return new ResponseEntity<>(anError, HttpStatus.BAD_REQUEST);
           }
           ScreeningResponse screeningResponse = new ScreeningResponse();
           screeningResponse.set(updatedScreening);
           return new ResponseEntity<>(screeningResponse, HttpStatus.CREATED);
       } catch (ResourceNotFoundException e) {
           ErrorResponse errorNotFound = new ErrorResponse();
            errorNotFound.set(e.getMessage());
           return new ResponseEntity<>(errorNotFound, HttpStatus.NOT_FOUND);
       }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<?>> deleteScreening(@PathVariable Long id, @PathVariable Long movieId) {
        try {
            ScreeningResponseDTO deletedScreening = screeningService.deleteScreening(id);
            if (deletedScreening != null && !deletedScreening.getMovieId().equals(movieId)) {
                ErrorResponse error = new ErrorResponse();
                error.set(ErrorConstants.SCREENING_NOT_MATCHING_MOVIE_MESSAGE);
                return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
            }
            return getResponseEntity(deletedScreening);
        } catch (ResourceNotFoundException e) {
            ErrorResponse error = new ErrorResponse();
            error.set(e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
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