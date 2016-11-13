package com.realdolmen.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import com.realdolmen.domain.Flight;
import com.realdolmen.domain.Partner;

@Stateless
public class FlightRepository {

	@PersistenceContext
	EntityManager em;

	public void create(Flight o) {
		em.persist(o);
	}

	public Flight read(Long id) {
		return em.find(Flight.class, id);
	}

	public String update(Flight o) {
		em.merge(o);
		return "success";
	}

	public void delete(Flight o) {
		em.remove(o);
	}

	public List<Flight> findAllFlights() {
		try {
			return em.createQuery("SELECT f FROM Flight f", Flight.class).getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}
	
	public List<Flight> getFlightsByCompany(Partner partner) {
		List<Flight> flights = em.createQuery("select f from Flight f where f.company = :company", Flight.class)
				.setParameter("company", partner.getCompany()).getResultList();
		if (flights == null || flights.isEmpty()) {
			return new ArrayList<Flight>();
		} else {
			return flights;
		}
	}
}