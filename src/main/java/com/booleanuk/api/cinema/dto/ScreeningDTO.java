package com.booleanuk.api.cinema.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class ScreeningDTO {

    private int id;

    @NotNull
    private int screenNumber;

    @NotNull
    private int capacity;

    @NotNull
    private LocalDateTime startsAt;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ScreeningDTO(int id, int screenNumber, int capacity, LocalDateTime startsAt, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.screenNumber = screenNumber;
        this.capacity = capacity;
        this.startsAt = startsAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }


}
