package com.booleanuk.api.cinema.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class ResponseObject <T>{
	public String status;
	public T data;

	public ResponseObject(String status){
		this.status = status;
		this.data = null;
	}
}
