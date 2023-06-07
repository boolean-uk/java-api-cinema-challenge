package com.booleanuk.api.cinema.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "screenings")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Screening extends CinemaEntity{

    @Column(name = "screen_number", nullable = false)
    private int screenNumber;
    @Column(name = "capacity", nullable = false)
    private int capacity;
    @Column(name = "starts_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-DD HH:MM:SS")
    private Date startsAt;

    @Column(name = "movie_id")
    private int movieId;
}
