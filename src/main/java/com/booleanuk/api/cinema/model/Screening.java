package com.booleanuk.api.cinema.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.ZonedDateTime;

@NoArgsConstructor
@Data
@Entity
@Table(name = "screenings")
@JsonIgnoreProperties({"movie"})
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer screeningId;
    @Column(nullable = false)
    private Integer screenNumber;
    @Column(nullable = false)
    private Integer capacity;
    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private ZonedDateTime startsAt;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private ZonedDateTime createdAt;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
    private ZonedDateTime updatedAt;
    @ManyToOne
    @JoinColumn(name = "movieId", referencedColumnName = "movieId", nullable = false)
    private Movie movie;
}
