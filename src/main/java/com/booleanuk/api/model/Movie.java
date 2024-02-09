package com.booleanuk.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String title;

    @Column
    private String rating;

    @Column
    private String description;

    @Column
    private int runTimeMins;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern ("yyyy-MM-dd ' ' HH:mm:ss");
        String dateTimeNow = LocalDateTime.now().format(dateTimeFormat);
        this.createdAt = LocalDateTime.parse(dateTimeNow, dateTimeFormat);
        this.updatedAt = LocalDateTime.parse(dateTimeNow, dateTimeFormat);
    }

    @PreUpdate
    public void preUpdate() {
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern ("yyyy-MM-dd ' ' HH:mm:ss");
        String dateTimeNow = LocalDateTime.now().format(dateTimeFormat);
        this.updatedAt = LocalDateTime.parse(dateTimeNow, dateTimeFormat);
    }

    public Movie(String title, String rating, String description, int runTimeMins) {
        this.title = title;
        this.rating = rating;
        this.description = description;
        this.runTimeMins = runTimeMins;
    }

    public Movie(int id) {
        this.id = id;
    }
}
