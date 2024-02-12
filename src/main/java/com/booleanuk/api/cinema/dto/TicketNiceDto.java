package com.booleanuk.api.cinema.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class TicketNiceDto {

    private String movie;
    private String rating;
    private String description;
    private int runtime;

    @JsonFormat(pattern = "uuuu-MM-dd'T'HH:mm:ss.SSSxxx")
    private OffsetDateTime startTime;
    private int screen;

    private int seats;

    public TicketNiceDto(String movie, String rating, String description, int runtime, int screen, OffsetDateTime startTime, int seats) {
        this.movie = movie;
        this.rating = rating;
        this.description = description;
        this.runtime = runtime;
        this.startTime = startTime;
        this.screen = screen;
        this.seats = seats;
    }
}
