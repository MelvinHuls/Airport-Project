package com.realdolmen.domain;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;

import com.realdolmen.enumerations.GlobalRegion;
import com.realdolmen.utilities.persistence.JpaPersistenceTest;

public class FlightTest extends JpaPersistenceTest {
	// private EntityManager em = entityManager();

	@Test
	public void makingAndRetrievingFlight() throws Exception {
		Location departure = new Location("Brussels airport", "Belgium", "BE", GlobalRegion.Western_Europe);
		Location destination = new Location("New York", "United States of America", "USA", GlobalRegion.North_America);
		entityManager().persist(departure);
		entityManager().persist(destination);
		Flight flight = new Flight("airliner", 300, 200, 10, new BigDecimal("300"), new BigDecimal("400"), new BigDecimal("600"), new Date(2016,12,01,8,30), new Date(2016,12,01,23,00), departure, destination);
		assertNotNull(flight);
		entityManager().persist(flight);
		assertNotNull(entityManager().find(Flight.class, flight.getId()));
	}
}
