package com.realdolmen.repository;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.realdolmen.domain.Client;
import com.realdolmen.domain.Flight;

@Stateless
public class ClientRepository2 {

	@PersistenceContext
	EntityManager em;

	public void create(Client o) {
		em.persist(o);
	}

	public Client read(Long id) {
		return em.find(Client.class, id);
	}

	public void update(Client o) {
		em.merge(o);
	}

	public void delete(Client o) {
		em.remove(o);
	}

	public List<Flight> getFlights() {
		List<Flight> flights = em.createQuery("select f from Flight f", Flight.class).getResultList();

		if (flights == null || flights.isEmpty()) {
			return new ArrayList<Flight>();
		} else {
			return flights;
		}
	}
}