package com.booleanuk.api.cinema.dto;

import java.time.LocalDateTime;

public class ScreeningDTO {
    private Integer screenNumber;
    private Integer capacity;
    private LocalDateTime startsAt;

    public ScreeningDTO() {
    }

    public Integer getScreenNumber() {
        return screenNumber;
    }

    public void setScreenNumber(Integer screenNumber) {
        this.screenNumber = screenNumber;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public LocalDateTime getStartsAt() {
        return startsAt;
    }

    public void setStartsAt(LocalDateTime startsAt) {
        this.startsAt = startsAt;
    }
}
