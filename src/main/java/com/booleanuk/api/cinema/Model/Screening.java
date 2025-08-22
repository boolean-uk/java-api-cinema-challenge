package com.booleanuk.api.cinema.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "screenings")
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private int id;
    @Setter
    @Getter
    @Column(name = "movieId")
    private int movieId;
    @Setter
    @Getter
    @Column(name = "screenNumber")
    private int screenNumber;
    @Setter
    @Getter
    @Column(name = "startsAt")
    private String startsAt;
    @Setter
    @Getter
    @Column(name = "capacity")
    private int capacity;
    @Setter
    @Getter
    @Column(name = "createdAt")
    private LocalDateTime createdAt;
    @Setter
    @Getter
    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    public Screening(int screenNumber, int capacity, String startsAt) {
        this.screenNumber = screenNumber;
        this.capacity = capacity;
        this.startsAt = startsAt;

        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        this.createdAt = LocalDateTime.parse(currentDateTime.format(formatter));
        this.updatedAt = LocalDateTime.parse(currentDateTime.format(formatter));
    }

}
