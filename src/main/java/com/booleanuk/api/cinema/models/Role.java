package com.booleanuk.api.cinema.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data // generates getters, toString, hashCode and equals
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING) // limit what values can be entered in the table
    @Column(length = 20)
    private ERole name;

    public Role(ERole name) {
        this.name = name;
    }
}