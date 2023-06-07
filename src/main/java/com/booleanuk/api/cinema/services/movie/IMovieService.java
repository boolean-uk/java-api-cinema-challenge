package com.booleanuk.api.cinema.services.movie;

import com.booleanuk.api.cinema.Dtos.movies.MovieRequestDto;
import com.booleanuk.api.cinema.Dtos.screenings.ScreeningNew;
import com.booleanuk.api.cinema.entities.Movie;
import com.booleanuk.api.cinema.entities.Screening;
import com.booleanuk.api.cinema.repositories.MovieRepo;
import com.booleanuk.api.cinema.repositories.ScreeningRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class IMovieService implements MovieServiceInterface {
    @Autowired
    MovieRepo movieRepo;
    @Autowired
    ScreeningRepo screeningRepo;

    @Override
    public Movie createMovie(MovieRequestDto movieDto) {

        Movie MovieInstance = movieRepo.save(movieDto.toMovie());
        List<ScreeningNew> screenings = movieDto.getScreenings();
        if (screenings != null) {
            for (ScreeningNew screening : screenings) {
                Screening sc = new Screening(0, MovieInstance, screening.getScreenNumber(), getInstant(screening), screening.getCapacity(), new ArrayList<>(), null, null);
                screeningRepo.save(sc);
            }
        }
//        System.out.println(screenings.size());
        return MovieInstance;
    }

    private Instant getInstant(ScreeningNew screening) {
        String date = screening.getStartsAt();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(date, format);
        return dateTime.toInstant(ZoneOffset.UTC);
    }

    @Override
    public List<Movie> getAllMovie() {
        return movieRepo.findAll();
    }

    private Movie findByIdOrElseThrow(int id) {
        return movieRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No Movie with that id were found"));
    }

    @Override
    public Movie updateMovie(int id, Movie movie) {
        Movie movieToUpdate = findByIdOrElseThrow(id);
        if (movie.getTitle() != null) {
            movieToUpdate.setTitle(movie.getTitle());
        }
        if (movie.getRating() != null) {
            movieToUpdate.setRating(movie.getRating());
        }
        if (movie.getDescription() != null) {
            movieToUpdate.setDescription(movie.getDescription());
        }
        if (movie.getRuntimeMins() != 0) {
            movieToUpdate.setRuntimeMins(movie.getRuntimeMins());
        }
        return movieRepo.save(movieToUpdate);
    }

    @Override
    public Movie deleteMovie(int id) {
        Movie movieTodelete = findByIdOrElseThrow(id);
        movieRepo.delete(movieTodelete);
        return movieTodelete;
    }
}
