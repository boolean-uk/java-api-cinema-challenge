package com.booleanuk.api.cinema.controller;

import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("movies")
public class MovieController {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private ScreeningRepository screeningRepository;
    public record MovieRequest(String title, String rating, String description, int runtimeMins , int[] screenings_id){}
    public record ScreeningRequest(int screenNumber, int capacity, String startsAt){}
    @GetMapping
    public List<Movie> getAll(){
        return this.movieRepository.findAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovie(@PathVariable int id){
        Movie movie = this.movieRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Movie with that id not found"));
        return new ResponseEntity<>(movie,HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody MovieRequest movie){
        //when creating a movie, we can add the screenings id in an array beforehand
        if(movie.title == null || movie.rating == null || movie.description == null || movie.runtimeMins == 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Movie body is invalid");
        }
        Movie temp = new Movie(movie.title, movie.rating, movie.description, movie.runtimeMins);
        if(movie.screenings_id != null) {
            for (int i = 0; i < movie.screenings_id.length; i++) {
                Screening screening = this.screeningRepository.findById(movie.screenings_id[i])
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No screening with given id exists"));
                temp.addScreening(screening);
            }
        }
        return new ResponseEntity<>(this.movieRepository.save(temp),HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable int id, @RequestBody MovieRequest movie){
        Movie updatedMovie = this.movieRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Movie with that id not found"));

        if(movie.title != null){
            updatedMovie.setTitle(movie.title);
            updatedMovie.setUpdatedAt();
        }
        if(movie.rating != null){
            updatedMovie.setRating(movie.rating);
            updatedMovie.setUpdatedAt();
        }
        if(movie.description != null){
            updatedMovie.setDescription(movie.description);
            updatedMovie.setUpdatedAt();
        }
        if(movie.runtimeMins != 0){
            updatedMovie.setRuntimeMins(movie.runtimeMins);
            updatedMovie.setUpdatedAt();
        }
        return new ResponseEntity<>(this.movieRepository.save(updatedMovie),HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Movie> deleteMovie(@PathVariable int id){
        Movie deleted = this.movieRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Movie with that id not found"));
        this.movieRepository.delete(deleted);
        return new ResponseEntity<>(deleted,HttpStatus.OK);
    }
    //under this comment is where everything regarding the screenings is.
    @PostMapping("/{id}/screenings")
    public ResponseEntity<Screening> createScreening(@PathVariable (name = "id") int id, @RequestBody ScreeningRequest screening){
        Movie screened = this.movieRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Movie with that id not found"));
        if(screening.screenNumber == 0 || screening.capacity == 0 || screening.startsAt == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Body of request is wrong");
        }
        Screening added = new Screening(screening.screenNumber,screening.capacity,screening.startsAt);
        added.setMoviePlaying(screened);
        screened.addScreening(added);
        return new ResponseEntity<>(this.screeningRepository.save(added),HttpStatus.CREATED);
    }
    @GetMapping("/{id}/screenings")
    public List<Screening> getScreenings(@PathVariable (name = "id") int id){
        //may need work
        List<Screening> screenings = this.screeningRepository.findAll().stream().filter((movie -> movie.getMoviePlaying().getId() == id)).toList();
        if(screenings.size()>0){
            return screenings;
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"That movie has no available screenings yet");
        }
    }
}
