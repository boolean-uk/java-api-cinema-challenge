package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "screenings")
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = true)
    @JsonIncludeProperties(value = {"id", "title"})
    private Movie movie;

    @Column
    private int screenNumber;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime startsAt;

    @Column
    private int capacity;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "screening")
    @JsonIgnoreProperties("screening")
    private List<Ticket> tickets;

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

    public Screening(int screenNumber, LocalDateTime startsAt, int capacity) {
        this.screenNumber = screenNumber;
        this.startsAt = startsAt;
        this.capacity = capacity;
    }

    public void setStartsAt(String startsAt) {
        this.startsAt = LocalDateTime.parse(startsAt);
    }
}
