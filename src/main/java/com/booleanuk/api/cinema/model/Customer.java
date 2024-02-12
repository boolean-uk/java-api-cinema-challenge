package com.booleanuk.api.cinema.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;
    @Column(name = "created_at")
    private Date createdAt;
    @Column(name = "updated_at")
    private Date updatedAt;

    @OneToMany(mappedBy = "customer")
    @JsonIgnoreProperties("customer")
    private List<Ticket> tickets;

    public Customer(String name, String email, String phone, Date createdAt, Date updatedAt) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    public Customer(int id) {
        this.id = id;
    }

    public void setUpdatedAtToCurrentTimeInGMTPlus1() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+1"));
        String formattedDate = sdf.format(new Date());
        try {
            this.updatedAt = sdf.parse(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
