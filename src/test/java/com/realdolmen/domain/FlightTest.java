package com.realdolmen.domain;

import org.junit.Before;
import org.junit.Test;

import com.realdolmen.utilities.persistence.JpaPersistenceTest;

public class FlightTest extends JpaPersistenceTest {

	private Flight flight;

	@Before
	public void setUp() {
		flight = new Flight();
	}

	@Test
	public void makingAndRetrievingFlight() throws Exception {
		assertNull(flight.getId());
		entityManager().persist(flight);
		assertNotNull(flight.getId());
	}
}
