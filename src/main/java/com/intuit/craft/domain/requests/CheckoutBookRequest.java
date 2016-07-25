package com.intuit.craft.domain.requests;

import java.util.UUID;


/**
 * The Class CheckoutBookRequest.
 * 
 * @author k0konat.
 */
public class CheckoutBookRequest {

	/** The id. */
	private UUID id;
	
	/** The name. */
	private String name;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public UUID getId() {
		return id;
	}
	
	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(UUID id) {
		this.id = id;
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
}
