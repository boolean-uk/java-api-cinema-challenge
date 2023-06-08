package com.booleanuk.api.cinema.service;

import com.booleanuk.api.cinema.model.Screening;
import com.booleanuk.api.cinema.repository.MovieRepository;
import com.booleanuk.api.cinema.repository.ScreeningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScreeningServiceImp implements ScreeningService{
    @Autowired
    private ScreeningRepository screeningRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Override
    public List<Screening> getScreenings(long id)
    {
        movieRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"No movie matches the provided id"));

        return screeningRepository.findAll();
    }

    @Override
    public Screening getScreening(long id) {
        return screeningRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Not found"));
    }

    @Override
    public Screening createScreening(long id, Screening screening) {

        try{

        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Incorrect statsAt date and time");
        }

        movieRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"No movie matches the provided id"));

        LocalDateTime createdAt = LocalDateTime.now();
        screening.setCreatedAt(createdAt);
        screening.setUpdatedAt(createdAt);
        return screeningRepository.save(screening);
    }

    @Override
    public Screening updateScreening(long id, Screening screening) {
        Screening foundScreening = screeningRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"No screening matches the provided id"));

        screening.setId(id);
        screening.setCreatedAt(foundScreening.getCreatedAt());
        screening.setUpdatedAt(LocalDateTime.now());
        return screeningRepository.save(screening);
    }

    @Override
    public Screening deleteScreening(long id) {
        Screening toBeDeleted = screeningRepository.findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"No screening matches the provided id"));

        screeningRepository.delete(toBeDeleted);
        return toBeDeleted;
    }
}
