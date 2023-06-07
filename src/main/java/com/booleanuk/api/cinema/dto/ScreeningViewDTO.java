package com.booleanuk.api.cinema.dto;

import com.booleanuk.api.cinema.model.Screening;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScreeningViewDTO {
    private int id;
    private int screenNumber;
    private int capacity;
    private Date startsAt;
    private Date createdAt;
    private Date updatedAt;

    public static ScreeningViewDTO fromScreening(Screening screening){
        return new ScreeningViewDTO(
                screening.getId(),
                screening.getScreenNumber(),
                screening.getCapacity(),
                screening.getStartsAt(),
                screening.getCreatedAt(),
                screening.getUpdatedAt()
        );
    }
}
