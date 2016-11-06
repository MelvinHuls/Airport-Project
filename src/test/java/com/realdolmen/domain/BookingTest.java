package com.realdolmen.domain;

import javax.persistence.EntityManager;

import org.junit.Test;

import com.realdolmen.utilities.persistence.JpaPersistenceTest;

public class BookingTest extends JpaPersistenceTest {

	/*@Test
	public void makingAndRetrievingBooking() throws Exception {
		Booking booking = new Booking();
		assertNotNull(booking);
		EntityManager em = entityManager();
		em.persist(booking);
		assertNotNull(em.find(Booking.class, booking.getId()));
	}*/
}