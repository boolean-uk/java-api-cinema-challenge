package com.booleanuk.api.cinema.model;

import com.booleanuk.api.cinema.StaticMethods;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import java.time.*;
import java.util.List;


@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @Email
    private String email;
    private String phone;
    @Column(name = "created_at")
    private String createdAt;
    @Column(name = "updated_at")
    private String updatedAt;
    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("customer")
    private List<Ticket> tickets;
    public Customer(){

    }

    public Customer(String name, String email, String phone) {
        super();
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.createdAt = StaticMethods.convertEpochTimeToDateTime(System.currentTimeMillis());
        this.updatedAt = StaticMethods.convertEpochTimeToDateTime(System.currentTimeMillis());
    }

    public void setUpdatedAt() {
        //when this method is called, the updatedAt value changes
        this.updatedAt = StaticMethods.convertEpochTimeToDateTime(System.currentTimeMillis());
    }
    public void setCreatedAt() {
        //when this method is called, the updatedAt value changes
        this.updatedAt = StaticMethods.convertEpochTimeToDateTime(System.currentTimeMillis());
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
