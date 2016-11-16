package com.realdolmen.repository;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import com.realdolmen.domain.Booking;

@Stateless
public class BookingRepository {

	@PersistenceContext
	EntityManager em;

	public Booking create(Booking o) {
		em.persist(o);
		return o;
	}

	public Booking read(Long id) {
		return em.find(Booking.class, id);
	}

	public String update(Booking o) {
		em.merge(o);
		return "success";
	}

	public Booking delete(Booking o) {
		em.remove(o);
		return o;
	}

	public List<Booking> findAll() {
		try {
			return em.createNamedQuery("SELECT b FROM Booking b", Booking.class).getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}
}