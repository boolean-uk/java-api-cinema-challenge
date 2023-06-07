package com.booleanuk.api.cinema.services.screening;

import com.booleanuk.api.cinema.Dtos.screenings.ScreeningDto;
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
public class IScreeningService implements ScreeningServiceInterface {

    @Autowired
    MovieRepo movieRepo;
    @Autowired
    ScreeningRepo screeningRepo;

    @Override
    public List<ScreeningDto> generateList(int id) {
        Movie movie = null;
        movie = movieRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No movie with that ID."));

        List<ScreeningDto> theList = new ArrayList<>();
        for (int i = 0; i < movie.getScreenings().size(); i++) {
            Screening screening = movie.getScreenings().get(i);
            ScreeningDto dto = new ScreeningDto(screening.getId()
                    , screening.getScreenNumber()
                    , screening.getStartsAt()
                    , screening.getCapacity()
                    , screening.getCreatedAt()
                    , screening.getUpdatedAt());
            theList.add(dto);
        }
        return theList;
    }

    @Override
    public ScreeningDto generateScreening(int id, ScreeningNew screening) {
        String date = screening.getStartsAt();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(date, format);
        Instant instant = dateTime.toInstant(ZoneOffset.UTC);

        Movie movie = null;
        movie = movieRepo.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No movie with that ID."));

        Screening tempScreening = new Screening(0, movie, screening.getScreenNumber(), instant, screening.getCapacity(), new ArrayList<>(), null, null);
        Screening screeningFromDb = screeningRepo.save(tempScreening);


        ScreeningDto dto = new ScreeningDto(screeningFromDb.getId()
                , screeningFromDb.getScreenNumber()
                , screeningFromDb.getStartsAt()
                , screeningFromDb.getCapacity()
                , screeningFromDb.getCreatedAt()
                , screeningFromDb.getUpdatedAt());
        return dto;
    }
}
