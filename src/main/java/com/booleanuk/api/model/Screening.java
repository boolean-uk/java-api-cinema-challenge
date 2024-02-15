package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "screenings")
@JsonIgnoreProperties("tickets")
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    @JsonIncludeProperties(value = {"title"})
    private Movie movie;

    @Column
    private int screenNumber;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    @Column
    private ZonedDateTime startsAt;

    @Column
    private int capacity;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    @Column
    private ZonedDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    @Column
    private ZonedDateTime updatedAt;

    @OneToMany(mappedBy = "screening")
    @JsonIgnoreProperties("screening")
    private List<Ticket> tickets;

    @PrePersist
    public void prePersist() {
        this.createdAt = ZonedDateTime.now();
        this.updatedAt = ZonedDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = ZonedDateTime.now();
    }

    public Screening(int screenNumber, ZonedDateTime startsAt, int capacity) {
        this.screenNumber = screenNumber;
        this.startsAt = startsAt;
        this.capacity = capacity;
    }

    public void setStartsAt(String timeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

        timeString = timeString.replace("+", ".000+");
        timeString = timeString.replace(' ', 'T');

        this.startsAt = ZonedDateTime.parse(timeString, formatter);
    }

    public ZonedDateTime getStartsAt() {
        return this.startsAt.withZoneSameInstant(ZoneId.systemDefault());
    }
    public ZonedDateTime getCreatedAt() {
        return this.updatedAt.withZoneSameInstant(ZoneId.systemDefault());
    }
    public ZonedDateTime getUpdatedAt() {
        return this.updatedAt.withZoneSameInstant(ZoneId.systemDefault());
    }
}
