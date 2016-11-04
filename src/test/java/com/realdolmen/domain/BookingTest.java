package com.realdolmen.domain;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.BeforeClass;
import org.junit.Test;

import com.realdolmen.utilities.persistence.JpaPersistenceTest;

public class BookingTest extends JpaPersistenceTest {
	private EntityManagerFactory entityManagerFactory;
	@BeforeClass
	public void setUp(){
		entityManagerFactory = Persistence.createEntityManagerFactory("theFactory");		
	}
	@Test
	public void makingAndRetrievingBooking() throws Exception {
		Booking booking = new Booking();
		assertNotNull(booking);
		EntityManager em = entityManagerFactory.createEntityManager();
		em.persist(booking);
		assertNotNull(em.find(Booking.class, booking.getId()));
	}
}
