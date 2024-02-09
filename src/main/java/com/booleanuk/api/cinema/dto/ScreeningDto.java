package com.booleanuk.api.cinema.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ScreeningDto {
    private int id;
    private int screenNumber;
    private Date startsAt;
    private int capacity;
    private Date createdAt;
    private Date updatedAt;

    public ScreeningDto(int id, int screenNumber, int capacity, Date startsAt, Date createdAt, Date updatedAt) {
        this.id = id;
        this.screenNumber = screenNumber;
        this.capacity = capacity;
        this.startsAt = startsAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
