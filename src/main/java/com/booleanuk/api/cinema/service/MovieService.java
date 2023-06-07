package com.booleanuk.api.cinema.service;

import com.booleanuk.api.cinema.GenericResponse;
import com.booleanuk.api.cinema.dto.MovieViewDTO;
import com.booleanuk.api.cinema.model.Movie;
import com.booleanuk.api.cinema.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {

    private final MovieRepository movieRepository;
    private final ScreeningService screeningService;

    @Autowired
    public MovieService(MovieRepository movieRepository, ScreeningService screeningService){
        this.movieRepository = movieRepository;
        this.screeningService = screeningService;
    }

    public GenericResponse<MovieViewDTO> create(Movie movie){
        Movie createdMovie;
        Movie movieWithoutScreenings = Movie.withoutScreenings(movie);

        try {
            createdMovie = movieRepository.save(movieWithoutScreenings);

            movie.getScreenings().forEach(screening -> {
                screeningService.create(createdMovie.getId(), screening);
            });
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not create movie, please check all required fields are correct");
        }

        return new GenericResponse<MovieViewDTO>().from(MovieViewDTO.from(createdMovie));
    }

    public GenericResponse<List<MovieViewDTO>> getAll(){
        List<Movie> movies = movieRepository.findAll();
        List<MovieViewDTO> movieViewDTOS = new ArrayList<>();

        movies.forEach(movie -> movieViewDTOS.add(MovieViewDTO.from(movie)));

        return new GenericResponse<List<MovieViewDTO>>().from(movieViewDTOS);
    }

    public GenericResponse<MovieViewDTO> update(int id, Movie movie){
        Movie movieToUpdate = movieRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "No movies matching that id were found"));

        if(movie.getTitle() != null)
            movieToUpdate.setTitle(movie.getTitle());
        if(movie.getRating() != null)
            movieToUpdate.setRating(movie.getRating());
        if(movie.getDescription() != null)
            movieToUpdate.setDescription(movie.getDescription());
        if(movie.getRuntimeMins() != 0)
            movieToUpdate.setRuntimeMins(movie.getRuntimeMins());

        try {
            return new GenericResponse<MovieViewDTO>().from(MovieViewDTO.from(movieRepository.save(movieToUpdate)));
        }
        catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Could not update the movie's details, please check all required fields are correct");
        }
    }

    public GenericResponse<MovieViewDTO> delete(int id){
        Movie movieToDelete = movieRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No movies matching that id were found"));
        movieRepository.delete(movieToDelete);

        return new GenericResponse<MovieViewDTO>().from(MovieViewDTO.from(movieToDelete));
    }
}
