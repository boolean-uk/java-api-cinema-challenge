package com.booleanuk.api.cinema.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "screenings")

public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private int screenNumber;
    @Column
    private int capacity;

    @Column
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ssxxx", shape = JsonFormat.Shape.STRING)
    private OffsetDateTime startsAt;

    @Column
    @CreationTimestamp
    private OffsetDateTime createdAt;
    @Column
    @UpdateTimestamp
    private OffsetDateTime updatedAt;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "movie_id", nullable = false)
    @JsonIncludeProperties(value = {"id","title", "rating", "runtimeMins", "description", "createdAt", "updatedAt"})
    private Movie movie;

    @OneToMany(mappedBy = "screening", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Ticket> tickets;

    public Screening(int screenNumber, int capacity, OffsetDateTime startsAt){
        OffsetDateTime currentDateTime = OffsetDateTime.now(ZoneOffset.UTC);
        this.screenNumber = screenNumber;
        this.capacity = capacity;
        this.startsAt = startsAt;
        this.createdAt = currentDateTime;
        this.updatedAt = this.createdAt;
    }
}
