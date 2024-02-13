package com.booleanuk.api.cinema.screening;

import com.booleanuk.api.cinema.ApiResponse;
import com.booleanuk.api.cinema.ErrorResponse;
import com.booleanuk.api.cinema.movie.Movie;
import com.booleanuk.api.cinema.movie.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("movies/{id}/screenings")
public class ScreeningController {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ScreeningRepository screeningRepository;

    @GetMapping
    public ResponseEntity<ApiResponse<?>> findScreenings(@PathVariable int id ){

        Movie movieToCheckScreeningsFor = getAMovie(id);
        if(movieToCheckScreeningsFor == null){
            return notFound();
        }

        ScreeningListResponse screeningListResponse = new ScreeningListResponse();
        screeningListResponse.set(movieToCheckScreeningsFor.getScreenings());

        return new ResponseEntity<>(screeningListResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createScreenings(@PathVariable int id, @RequestBody Screening screening){
        if(isInvalidRequest(screening)){
            return badRequest();
        }
        Movie movieToAddScreeningFor = getAMovie(id);
        if(movieToAddScreeningFor == null){
            return notFound();
        }
        screening.setMovie(movieToAddScreeningFor);
        Screening createdScreening = screeningRepository.save(screening);

        ScreeningResponse response = new ScreeningResponse();
        response.set(createdScreening);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    private Movie getAMovie(int id){
        return this.movieRepository.findById(id).orElse(null);
    }

    private boolean isInvalidRequest(Screening screening){
        return screening.getScreenNumber() == 0 || screening.getCapacity() == 0 || screening.getStartsAt() == null;
    }
    private ResponseEntity<ApiResponse<?>> badRequest(){
        ErrorResponse error = new ErrorResponse();
        error.set("Could not create screening for the specified movie, please check all required fields are correct");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ApiResponse<?>> notFound(){
        ErrorResponse error = new ErrorResponse();
        error.set("No movie with that id were found");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}
