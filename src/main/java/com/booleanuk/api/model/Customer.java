package com.booleanuk.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private String email;

    @Column
    private String phone;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "customer")
    @JsonIgnoreProperties("customer")
    private List<Ticket> tickets;

    @PrePersist
    public void prePersist() {
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern ("yyyy-MM-dd ' ' HH:mm:ss");
        String dateTimeNow = LocalDateTime.now().format(dateTimeFormat);
        this.createdAt = LocalDateTime.parse(dateTimeNow, dateTimeFormat);
        this.updatedAt = LocalDateTime.parse(dateTimeNow, dateTimeFormat);
    }

    @PreUpdate
    public void preUpdate() {
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern ("yyyy-MM-dd ' ' HH:mm:ss");
        String dateTimeNow = LocalDateTime.now().format(dateTimeFormat);
        this.updatedAt = LocalDateTime.parse(dateTimeNow, dateTimeFormat);
    }

    public Customer(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public Customer(int id) {
        this.id = id;
    }
}
