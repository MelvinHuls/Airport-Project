package com.realdolmen.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Booking {
	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	@NotNull
	private User client;

	@ManyToOne
	private Flight incoming;

	@ManyToOne
	@NotNull
	private Flight outgoing;

	protected Booking() {
	}

	public Booking(User client, Flight flight) {
		this.client = client;
		this.outgoing = flight;
	}

	public Booking(User client, Flight flight1, Flight flight2) {
		this.client = client;
		this.incoming = flight1;
		this.outgoing = flight2;
	}

	public User getClient() {
		return client;
	}

	public void setClient(User client) {
		this.client = client;
	}

	public Flight getIncoming() {
		return incoming;
	}

	public void setIncoming(Flight incoming) {
		this.incoming = incoming;
	}

	public Flight getOutgoing() {
		return outgoing;
	}

	public void setOutgoing(Flight outgoing) {
		this.outgoing = outgoing;
	}

	public Long getId() {
		return id;
	}
}
