package com.booleanuk.api.model;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;


@NoArgsConstructor
@Setter
@Getter
@Entity
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
    private LocalDateTime startsAt;
    @Column
    private LocalDateTime createdAt;
    @Column
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    @JsonIncludeProperties(value = {"id","title", "rating","description", "runtimeMins", "createdAt","updatedAt"})
    private Movie movie;

    @OneToMany(mappedBy = "screening")
    @JsonIncludeProperties(value = {"id","screenNumber","capacity","startsAt","createdAt","updatedAt"})
    private List<Ticket> tickets;

    public Screening(int id){
        this.id = id;
    }

    public Screening(int screenNumber, int capacity, LocalDateTime startsAt, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.screenNumber = screenNumber;
        this.capacity = capacity;
        this.startsAt = startsAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = LocalDateTime.now();
    }
}
