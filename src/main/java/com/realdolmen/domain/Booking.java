package com.realdolmen.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Booking {

	@Id
	@GeneratedValue
	private Long id;
	private User client;
	private Flight flight;
	private Integer numberOfEconomyTickets;
	private Integer numberOfBusinessTickets;
	private Integer numberOfFirstClassTickets;

	public Booking() {
	}

	public Booking(User client, Flight flight, Integer numberOfEconomyTickets, Integer numberOfBusinessTickets,
			Integer numberOfFirstClassTickets) {
		this.client = client;
		this.flight = flight;
		this.numberOfEconomyTickets = numberOfEconomyTickets;
		this.numberOfBusinessTickets = numberOfBusinessTickets;
		this.numberOfFirstClassTickets = numberOfFirstClassTickets;
	}

	public User getClient() {
		return client;
	}

	public void setClient(User client) {
		this.client = client;
	}

	public Flight getFlight() {
		return flight;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}

	public Integer getNumberOfEconomyTickets() {
		return numberOfEconomyTickets;
	}

	public void setNumberOfEconomyTickets(Integer numberOfEconomyTickets) {
		this.numberOfEconomyTickets = numberOfEconomyTickets;
	}

	public Integer getNumberOfBusinessTickets() {
		return numberOfBusinessTickets;
	}

	public void setNumberOfBusinessTickets(Integer numberOfBusinessTickets) {
		this.numberOfBusinessTickets = numberOfBusinessTickets;
	}

	public Integer getNumberOfFirstClassTickets() {
		return numberOfFirstClassTickets;
	}

	public void setNumberOfFirstClassTickets(Integer numberOfFirstClassTickets) {
		this.numberOfFirstClassTickets = numberOfFirstClassTickets;
	}

	public Long getId() {
		return id;
	}
}
