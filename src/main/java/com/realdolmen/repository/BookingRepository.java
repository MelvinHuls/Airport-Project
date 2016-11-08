package com.realdolmen.repository;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.realdolmen.domain.Booking;

@Stateless
public class BookingRepository {

	@PersistenceContext
	EntityManager em;

	public void create(Booking o) {
		em.persist(o);
	}

	public Booking read(Long id) {
		return em.find(Booking.class, id);
	}

	public void update(Booking o) {
		em.merge(o);
	}

	public void delete(Booking o) {
		em.remove(o);
	}
}