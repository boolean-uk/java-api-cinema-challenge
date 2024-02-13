package com.booleanuk.api.cinema.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
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

    @Column
    private Integer screenNumber;

    @Column(name = "starts_at")
    private LocalDateTime startsAt;

    @Column
    private Integer capacity;

    @ManyToOne
    @JsonIgnoreProperties (value = "screening")
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @OneToMany(mappedBy = "screening")
    @JsonIgnoreProperties(value = {"screening"},  allowSetters = true)
    private List<Ticket> tickets;

    public Screening(int screenNumber, LocalDateTime startsAt, int capacity) {
        this.screenNumber = screenNumber;
        this.startsAt = startsAt;
        this.capacity = capacity;
    }

    public Screening(int id){
        this.id = id;
    }
}