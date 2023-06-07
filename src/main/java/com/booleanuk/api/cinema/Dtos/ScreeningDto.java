package com.booleanuk.api.cinema.Dtos;

import com.booleanuk.api.cinema.entities.Movie;
import com.booleanuk.api.cinema.entities.Screening;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Data
public class ScreeningDto {
    private Integer screenNumber;
    private Integer capacity;
    private String startsAt;

    public Screening toScreening(Movie movie) {
        return new Screening(0, movie, screenNumber, getInstant(), capacity, null, null, new ArrayList<>());
    }

    private Instant getInstant() {
        String date = startsAt;
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(date, format);
        return dateTime.toInstant(ZoneOffset.UTC);
    }
}
