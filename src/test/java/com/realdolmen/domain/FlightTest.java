package com.realdolmen.domain;

import javax.persistence.EntityManager;

import org.junit.Test;

import com.realdolmen.utilities.persistence.JpaPersistenceTest;

public class FlightTest extends JpaPersistenceTest {

	@Test
	public void makingAndRetrievingFlight() throws Exception {
		Flight flight = new Flight();
		assertNotNull(flight);
		EntityManager em = entityManager();
		em.persist(flight);
		assertNotNull(em.find(Flight.class, flight.getId()));
	}
}
