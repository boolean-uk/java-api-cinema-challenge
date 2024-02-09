package com.booleanuk.api.cinema.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "screenings")
@JsonIgnoreProperties({"movies"})
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int screeningsId;

    @Column(nullable = false)
    private int screenNumber;

    @Column(nullable = false)
    private int capacity;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private ZonedDateTime startsAt;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private ZonedDateTime createdAt;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private ZonedDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "movieId", referencedColumnName = "movieId")
    private Movie movie;
}
