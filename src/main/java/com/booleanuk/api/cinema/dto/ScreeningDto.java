package com.booleanuk.api.cinema.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ScreeningDto {
    private int id;
    private int screenNumber;
    private LocalDateTime startsAt;
    private int capacity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ScreeningDto(int id, int screenNumber, int capacity, LocalDateTime startsAt, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.screenNumber = screenNumber;
        this.capacity = capacity;
        this.startsAt = startsAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
