package com.realdolmen.domain;

import org.junit.Test;

import com.realdolmen.utilities.persistence.JpaPersistenceTest;

public class FlightTest extends JpaPersistenceTest {
	// private EntityManager em = entityManager();

	@Test
	public void makingAndRetrievingFlight() throws Exception {
		Location departure = new Location("Brussels airport", "Belgium", "BE", GlobalRegion.Western_Europe);
		Location destination = new Location("New York", "United States of America", "USA", GlobalRegion.North_America);
		entityManager().persist(departure);
		entityManager().persist(destination);
		Flight flight = new Flight("airliner", 200, 300, 500, departure, destination);
		assertNotNull(flight);
		entityManager().persist(flight);
		assertNotNull(entityManager().find(Flight.class, flight.getId()));
	}
}
