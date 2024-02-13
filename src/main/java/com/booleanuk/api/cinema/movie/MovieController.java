package com.booleanuk.api.cinema.movie;

import com.booleanuk.api.cinema.responses.ErrorResponse;
import com.booleanuk.api.cinema.responses.Response;
import com.booleanuk.api.cinema.screening.Screening;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("movies")
public class MovieController {
    @Autowired
    private MovieRepository repo;

    @PostMapping
    public ResponseEntity<Response<?>> create(@RequestBody Movie movie){
        if (movie.getTitle() == null ||
                movie.getRating() == null ||
                movie.getDescription() == null ||
                movie.getRuntimeMins() <= 0){
            ErrorResponse badRequest = new ErrorResponse();
            badRequest.set("bad request");

            return new ResponseEntity<>(badRequest, HttpStatus.BAD_REQUEST);
        }

        movie.setCreatedAt(nowFormatted());
        movie.setUpdatedAt(nowFormatted());

        if (movie.getScreenings() != null && !movie.getScreenings().isEmpty()) {
            for (Screening screening : movie.getScreenings()) {
                screening.setMovie(movie);
                screening.setCreatedAt(nowFormatted());
                screening.setUpdatedAt(nowFormatted());
            }
        }

        repo.save(movie);
        Response<Movie> movieResponse = new Response<>();
        movieResponse.set(movie);

        return new ResponseEntity<>(movieResponse, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Response<List<Movie>>> getAll(){
        Response<List<Movie>> movieListResponse = new Response<>();
        movieListResponse.set(repo.findAll());

        return ResponseEntity.ok(movieListResponse);
    }

    @GetMapping("{id}")
    public ResponseEntity<Response<?>> getOne(@PathVariable int id){
        Movie movie = getById(id);

        if (movie == null){
            ErrorResponse notFound = new ErrorResponse();
            notFound.set("not found");

            return new ResponseEntity<>(notFound, HttpStatus.NOT_FOUND);
        }

        Response<Movie> movieResponse = new Response<>();
        movieResponse.set(movie);

        return ResponseEntity.ok(movieResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<Response<?>> update(@PathVariable int id, @RequestBody Movie movie){
        if (movie.getRuntimeMins() != 0 && movie.getRuntimeMins() <= 0){
            ErrorResponse badRequest = new ErrorResponse();
            badRequest.set("bad request");

            return new ResponseEntity<>(badRequest, HttpStatus.BAD_REQUEST);
        }

        Movie toUpdate = getById(id);

        if (toUpdate == null){
            ErrorResponse notFound = new ErrorResponse();
            notFound.set("not found");

            return new ResponseEntity<>(notFound, HttpStatus.NOT_FOUND);
        }

        Optional.ofNullable(movie.getTitle())
                .ifPresent(title -> toUpdate.setTitle(title));
        Optional.ofNullable(movie.getRating())
                .ifPresent(rating -> toUpdate.setRating(rating));
        Optional.ofNullable(movie.getDescription())
                .ifPresent(description -> toUpdate.setDescription(description));
        Optional.of(movie.getRuntimeMins())
                .ifPresent(runtimeMins -> toUpdate.setRuntimeMins(runtimeMins));

        toUpdate.setUpdatedAt(nowFormatted());
        repo.save(toUpdate);
        Response<Movie> movieResponse = new Response<>();
        movieResponse.set(toUpdate);

        return new ResponseEntity<>(movieResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Response<?>> delete(@PathVariable int id){
        Movie toDelete = getById(id);

        if (toDelete == null){
            ErrorResponse notFound = new ErrorResponse();
            notFound.set("not found");

            return new ResponseEntity<>(notFound, HttpStatus.NOT_FOUND);
        }

        repo.delete(toDelete);
        toDelete.setScreenings(new ArrayList<Screening>());
        Response<Movie> movieResponse = new Response<>();
        movieResponse.set(toDelete);

        return ResponseEntity.ok(movieResponse);
    }

    private Movie getById(int id){
        return repo.findById(id).orElse(null);
    }

    private String nowFormatted(){
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return now.format(format);
    }
}
