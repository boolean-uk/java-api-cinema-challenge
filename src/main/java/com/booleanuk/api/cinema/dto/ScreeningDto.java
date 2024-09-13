package com.booleanuk.api.cinema.dto;

import com.booleanuk.api.cinema.models.Screening;

import java.time.LocalDateTime;

public class ScreeningDto {
    private int screenNumber;
    private int capacity;
    private LocalDateTime statsAt;

    public ScreeningDto(Screening screening) {
        this.screenNumber = screening.getScreenNumber();
        this.capacity = screening.getCapacity();
        this.statsAt = screening.getStartsAt();
    }

    public int getScreenNumber() {
        return screenNumber;
    }

    public void setScreenNumber(int screenNumber) {
        this.screenNumber = screenNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public LocalDateTime getStatsAt() {
        return statsAt;
    }

    public void setStatsAt(LocalDateTime statsAt) {
        this.statsAt = statsAt;
    }
}
