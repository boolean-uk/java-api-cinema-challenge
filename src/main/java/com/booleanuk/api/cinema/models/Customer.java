package com.booleanuk.api.cinema.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.text.DateFormat;
import java.time.Clock;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "customer")
public class Customer extends Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;
    @Column(nullable = false)
    private String email;
    @Column
    private String phone;
    @Column
    private String createdAt;
    @Column
    private String updatedAt;

    @OneToMany(mappedBy = "customer_id")
    @JsonIgnoreProperties("customer_id")
    private List<Ticket> tickets;

    public Customer() {
        createdAt = getCurrentDate();
        updatedAt = createdAt;
    }

    public Customer(final String name, final String email, final String phone, final String created_at, final String updated_at) {
        this.name = name;
        this.email = email;
        this.phone = phone;

        this.createdAt = getCurrentDate();
        this.updatedAt = this.createdAt;
    }

    @Override
    public boolean haveNullFields() {
        return name == null || email == null || phone == null;
    }

    @Override
    public void copyOverData(Model model) {
        try {
            Customer _other = (Customer) model;

            name = _other.name;
            email = _other.email;
            phone = _other.phone;

            updatedAt = getCurrentDate(); // get the current date
            tickets = _other.tickets;
        }
        catch (Exception e) {}
    }

    private String getCurrentDate() {
        return DateFormat.getDateTimeInstance().format(new Date());
    }
}
