package com.booleanuk.api.cinema.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
public class Customer extends TimestampedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(index = 1)
    private long id;
    @NotNull
    @JsonProperty(index = 2)
    private String name;
    @NotNull
    @Email
    @JsonProperty(index = 3)
    private String email;
    @NotNull
    @JsonProperty(index = 4)
    private String phone;

    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    List<Ticket> tickets;

    public Customer() {
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}