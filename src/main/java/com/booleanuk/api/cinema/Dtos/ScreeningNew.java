package com.booleanuk.api.cinema.Dtos;

import lombok.Data;

import java.time.Instant;
@Data

public class ScreeningNew {

    private int screenNumber;
    private int capacity;
    private String startsAt;
}
