package com.booleanuk.api.cinema.model;

import com.booleanuk.api.cinema.formatter.EmailDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "customers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends CinemaEntity{


    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "email", nullable = false)
    @JsonDeserialize(using = EmailDeserializer.class)
    private String email;
    @Column(name = "phone", nullable = false)
    private String phone;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Ticket> tickets;

}
