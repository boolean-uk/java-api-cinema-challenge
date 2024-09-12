package com.booleanuk.api.cinema.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    int screenNumber;

    @Column
    int capacity;

    @ManyToOne
    @JoinColumn(name="movieid", nullable = false)
    private Movie movie;

    @OneToMany(mappedBy = "screening")
    private List<Ticket> tickets;


}
