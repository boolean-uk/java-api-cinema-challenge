package com.booleanuk.api.cinema.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CustomerDTO {

	private int id;
	private String name;
	private String email;
	private String phone;
	private String createdAt;
	private String updatedAt;
	 @JsonIgnore
	private List<Ticket> tickets;

	public CustomerDTO(int id, String name, String email, String phone, String createdAt, String updatedAt, List<Ticket> tickets) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.tickets = tickets;
	}
}
