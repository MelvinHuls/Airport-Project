package com.realdolmen.repository;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import com.realdolmen.domain.Flight;

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

	public void update(Flight o) {
		em.merge(o);
	}

	public void delete(Flight o) {
		em.remove(o);
	}

	public List<Flight> findAll() {
		try {
			return em.createNamedQuery("SELECT f FROM Flight f", Flight.class).getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}
}