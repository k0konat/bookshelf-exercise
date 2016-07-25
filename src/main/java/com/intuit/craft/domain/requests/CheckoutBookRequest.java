package com.intuit.craft.domain.requests;

import java.util.UUID;

public class CheckoutBookRequest {

	private UUID id;
	private String name;

	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
