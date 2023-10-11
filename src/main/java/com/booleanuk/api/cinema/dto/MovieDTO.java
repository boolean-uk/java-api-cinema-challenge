package com.booleanuk.api.cinema.dto;

import com.booleanuk.api.cinema.model.Screening;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class MovieDTO {

    private int id;

    @NotNull
    @NotBlank
    private String title;

    @NotNull
    @NotBlank
    private String rating;

    @NotNull
    @NotBlank
    private String description;

    @NotNull
    private int runtimeMins;

    List<Screening> screenings;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public MovieDTO(int id, String title, String rating, String description, int runtimeMins, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.rating = rating;
        this.description = description;
        this.runtimeMins = runtimeMins;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
