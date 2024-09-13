package com.booleanuk.api.cinema.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    private String startsAt;

    @Column
    private LocalDateTime createdAt=LocalDateTime.now();

    @Column
    private LocalDateTime updatedAt=LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name="movieid", nullable = false)
    private Movie movie;

    @OneToMany(mappedBy = "screening")
    @JsonIgnoreProperties(value ={"screenings", "tickets", "movie","customer","screening"})
    private List<Ticket> tickets;


}
