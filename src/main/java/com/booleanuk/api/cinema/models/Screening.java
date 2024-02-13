package com.booleanuk.api.cinema.models;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

/**
 * Class holding information about a screening
 */
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
    @JoinColumn(name = "movie_id")
    @JsonIgnore
    private Movie movie;

    @Column
    private int screenNumber;

    @Column
    private ZonedDateTime startsAt;

    @Column
    private int capacity;

    @Column
    @CreationTimestamp
    private ZonedDateTime createdAt;
    @PrePersist
    protected void onCreate()   {
        updatedAt = createdAt = ZonedDateTime.now();
    }

    @Column
    @UpdateTimestamp
    private ZonedDateTime updatedAt;
    @PreUpdate
    protected void onUpdate()   {
        updatedAt = ZonedDateTime.now();
    }

    public Screening(
            Movie movie,
            int screenNumber,
            String startsAt,
            int capacity
    )
    {
        this.movie          = movie;
        this.screenNumber   = screenNumber;
        TemporalAccessor ta = DateTimeFormatter.ISO_ZONED_DATE_TIME.parse(startsAt);
        Instant i           = Instant.from(ta);
        this.startsAt       = ZonedDateTime.from(i);
        this.capacity       = capacity;
    }

    public boolean verifyScreening()    {
        return this.getScreenNumber() != 0
                && this.getCapacity() != 0
                && this.getStartsAt() != null;
    }
}
