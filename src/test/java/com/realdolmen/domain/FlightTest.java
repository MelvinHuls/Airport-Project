package com.realdolmen.domain;

import javax.persistence.EntityManager;

import org.junit.Test;

import com.realdolmen.utilities.persistence.JpaPersistenceTest;

public class FlightTest extends JpaPersistenceTest {

	@Test
	public void makingAndRetrievingFlight() throws Exception {
		EntityManager em = entityManager();
		Location departure = new Location("Brussels airport", "Belgium", "BE", GlobalRegion.Western_Europe);
		Location destination = new Location("New York", "United States of America", "USA", GlobalRegion.North_America);
		em.persist(departure);
		em.persist(destination);
		Flight flight = new Flight("airliner", 200, 300, 500, departure, destination);
		assertNotNull(flight);
		em.persist(flight);
		assertNotNull(em.find(Flight.class, flight.getId()));
	}
}
