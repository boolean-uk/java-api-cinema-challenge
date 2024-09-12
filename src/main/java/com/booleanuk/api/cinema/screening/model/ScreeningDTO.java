package com.booleanuk.api.cinema.screening.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class ScreeningDTO {
    /*
    DTO to accept time format specified in api spec.
    Used as a middle man to convert to the desired time format when storing in database.
    */

    public ScreeningDTO (int screenNumber, int capacity, String startsAt) {
        this.screenNumber = screenNumber;
        this.capacity = capacity;
        this.startsAt = startsAt;
    }

    private int screenNumber;

    private String startsAt;

    private int capacity;

}
