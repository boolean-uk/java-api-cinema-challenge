package com.booleanuk.api.cinema.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString

@Entity
@Table(name="Customers")
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column
	private String name;
	
	@Column
	private String email;
	
	@Column
	private String phone;

	@Column
	private OffsetDateTime createdAt;

	@Column
	private OffsetDateTime updatedAt;

	@OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
	@JsonIgnoreProperties(value = {"customer" }) // ignorer customer sine tickets.
	@JsonIgnore
	private List<Ticket> tickets;


	public void createdNow(){
		OffsetDateTime n = OffsetDateTime.now();
		this.createdAt = n;
		this.updatedAt = n;
	}

	public void updatedNow(){
		this.updatedAt = OffsetDateTime.now();
	}
}
