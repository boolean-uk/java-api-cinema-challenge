package com.booleanuk.api.cinema.controllers;

import com.booleanuk.api.cinema.models.Movie;
import com.booleanuk.api.cinema.models.Screening;
import com.booleanuk.api.cinema.repositories.MovieRepository;
import com.booleanuk.api.cinema.repositories.ScreeningRepository;
import com.booleanuk.api.cinema.responses.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("movies")
public class ScreeningController {
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ScreeningRepository screeningRepository;

    /**
     * Gets all screenings for the specified movie
     * @param id - ID of movie
     * @return List of Screening objects
     */
    @GetMapping("{id}/screenings")
    public ResponseEntity<Response<?>> getOneMovie(@PathVariable int id)  {
        // Check the movie id
        Movie movie = this.movieRepository.findById(id)
                .orElse(null);
        if (movie == null)
        {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("No movie with that id found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        ScreeningListResponse screeningListResponse = new ScreeningListResponse();
        screeningListResponse.set(
                "Successfully returned a list of all screenings for the specified movie",
                movie.getScreenings()
        );
        return ResponseEntity.ok(screeningListResponse);
    }

    /**
     * Creates a screening for the specified movie
     * Request Body:
     *  screenNumber: int REQUIRED
     *  capacity: int REQUIRED
     *  startsAt: Date REQUIRED
     *
     * @param id - ID of movie to add screening to
     * @param screening - Screening to add
     * @return Response code signifying success/failure, and screening which was added to the database
     * @see Screening ::verifyScreening()
     */
    @PostMapping("{id}/screenings")
    public ResponseEntity<Object> createScreening(@PathVariable int id, @RequestBody Screening screening)    {
        // Check if screening has all required fields
        if(!screening.verifyScreening())
        {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set(
                    "Could not create a screening for the specified movie, please check all fields are correct"
            );
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        // Check movie id
        Movie movieToScreen = this.movieRepository.findById(id)
                .orElse(null);
        if(movieToScreen == null)
        {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("No Movie with that field found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        // Update values of screening
        // screening.setStartsAt(screening.getStartsAt()); Why?
        screening.setMovie(movieToScreen);
        movieToScreen.addScreening(screening);
        this.movieRepository.save(movieToScreen);
        this.screeningRepository.save(screening);

        ScreeningResponse screeningResponse = new ScreeningResponse();
        screeningResponse.set("Successfully created a new screening for the specified movie", screening);
        return new ResponseEntity<>(screeningResponse, HttpStatus.CREATED);
    }

    /**
     * Update a screening
     * @param id - The id of the movie
     * @param screening - The screening request body to use for updating
     * @return Response enity with the results of the request
     */
    @PutMapping("{id}/screenings")
    public ResponseEntity<Response<?>> updateScreening(@PathVariable int id, @RequestBody Screening screening)    {
        // Check screening id
        Screening screeningToUpdate = this.screeningRepository.findById(id)
                .orElse(null);
        if(screeningToUpdate == null)
        {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.set("No screening with that id found");
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        if(screening.getScreenNumber() != 0) screeningToUpdate.setScreenNumber(screening.getScreenNumber());
        if(screening.getCapacity() != 0)     screeningToUpdate.setCapacity(screening.getCapacity());
        if(screening.getStartsAt() != null)  screeningToUpdate.setStartsAt(screening.getStartsAt());

        ScreeningResponse screeningResponse = new ScreeningResponse();
        screeningResponse.set("Successfully updated screening", screeningToUpdate);
        return new ResponseEntity<>(screeningResponse, HttpStatus.CREATED);
    }
}
