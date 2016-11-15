package com.realdolmen.beans;

import java.util.Date;

import org.junit.Test;

import com.realdolmen.domain.Flight;
import com.realdolmen.domain.Location;
import com.realdolmen.enumerations.GlobalRegion;
import com.realdolmen.utilities.persistence.JpaPersistenceTest;

public class FlightBeanTest extends JpaPersistenceTest{
	
	@Test
	public void getFlightTest() {
		FlightBean fbean = new FlightBean();
		fbean.setEm(entityManager());

		Location departure = new Location("Brussels airport", "Belgium", "BE", GlobalRegion.WESTERN_EUROPE);
		Location destination = new Location("New York", "United States of America", "USA", GlobalRegion.NORTH_AMERICA);
		entityManager().persist(departure);
		entityManager().persist(destination);
		Flight flight = new Flight("airliner", 300, 200, 10, 300d, 400d, 600d, new Date(2016,12,01,8,30), new Date(2016,12,01,23,00), departure, destination);
		entityManager().persist(flight);
		Flight retreivedFlight = fbean.getFlight(flight.getId());
		assertNotNull(retreivedFlight);
		assertEquals(flight.getId(), retreivedFlight.getId());
	}

}
