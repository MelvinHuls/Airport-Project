package com.realdolmen.beans;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import com.realdolmen.Exceptions.LackingSearchCriteriaException;
import com.realdolmen.domain.Flight;
import com.realdolmen.domain.FlightSearch;
import com.realdolmen.domain.Location;
import com.realdolmen.domain.Partner;
import com.realdolmen.enumerations.FlightClass;
import com.realdolmen.enumerations.GlobalRegion;
import com.realdolmen.utilities.persistence.JpaPersistenceTest;

public class FlightSearchBeanTest extends JpaPersistenceTest {

	@Test
	public void getAllFlights() {
		FlightSearchBean fsbean = new FlightSearchBean();
		fsbean.setEm(entityManager());
		Location departure = new Location("Brussels airport", "Belgium", "BE", GlobalRegion.WESTERN_EUROPE);
		Location destination = new Location("New York", "United States of America", "USA", GlobalRegion.NORTH_AMERICA);
		entityManager().persist(departure);
		entityManager().persist(destination);
		Flight flight = new Flight("airliner", 300, 200, 10, 300d, 400d, 600d, new Date(2016, 12, 01, 8, 30),
				new Date(2016, 12, 01, 23, 00), departure, destination);
		entityManager().persist(flight);
		List<Flight> allFlights = fsbean.getAllFlights();
		assertNotNull(allFlights);
		assertNotEquals(0, allFlights.size());
		assertEquals(flight.getId(), allFlights.get(0).getId());
	}

	@Test
	public void getAllCompanies() {
		FlightSearchBean fsbean = new FlightSearchBean();
		fsbean.setEm(entityManager());
		Partner user = new Partner("Freddy", "fourlettersalluppercase", "FDoubleU@gmail.com", "RocketTrans");
		entityManager().persist(user);
		List<String> allCompanies = fsbean.getCompanies();
		assertNotNull(allCompanies);
		assertNotEquals(0, allCompanies.size());
		Iterator<String> it = allCompanies.iterator();
		String name = null;
		while (it.hasNext()) {
			if (it.next().equals("RocketTrans")) {
				name = "RocketTrans";
			}
		}
		assertNotNull(name);
		assertEquals("RocketTrans", name);
	}

	@Test
	public void getAllCountries() {
		FlightSearchBean fsbean = new FlightSearchBean();
		fsbean.setEm(entityManager());
		Location departure = new Location("Brussels airport", "Belgium", "BE", GlobalRegion.WESTERN_EUROPE);
		entityManager().persist(departure);
		List<String> allCountries = fsbean.getCountries();
		assertNotNull(allCountries);
		assertNotEquals(0, allCountries.size());
		Iterator<String> it = allCountries.iterator();
		String name = null;
		while (it.hasNext()) {
			if (it.next().equals("Belgium")) {
				name = "Belgium";
			}
		}
		assertNotNull(name);
		assertEquals("Belgium", name);
	}

	@Test
	public void getAirportsCountry() {
		FlightSearchBean fsbean = new FlightSearchBean();
		fsbean.setEm(entityManager());
		Location departure = new Location("Brussels airport", "Belgium", "BE", GlobalRegion.WESTERN_EUROPE);
		entityManager().persist(departure);
		List<String> allAirport = fsbean.getAirportsCountry("Belgium");
		assertNotNull(allAirport);
		assertNotEquals(0, allAirport.size());
		Iterator<String> it = allAirport.iterator();
		String name = null;
		while (it.hasNext()) {
			if (it.next().equals("Brussels airport")) {
				name = "Brussels airport";
			}
		}
		assertNotNull(name);
		assertEquals("Brussels airport", name);
	}

	@Test
	public void getFlightsUsingSearchCriteria() throws LackingSearchCriteriaException {
		FlightSearchBean fsbean = new FlightSearchBean();
		fsbean.setEm(entityManager());
		Location departure = new Location("Brussels airport", "Belgium", "BE", GlobalRegion.WESTERN_EUROPE);
		Location destination = new Location("New York", "United States of America", "USA", GlobalRegion.NORTH_AMERICA);
		entityManager().persist(departure);
		entityManager().persist(destination);
		Flight flight = new Flight("airliner", 300, 200, 10, 300d, 400d, 600d, new Date(2016, 12, 01, 8, 30),
				new Date(2016, 12, 01, 23, 00), departure, destination);
		entityManager().persist(flight);

		FlightSearch flightSearch = new FlightSearch();
		flightSearch.setSeats(2);
		flightSearch.setDateDeparture(new Date(2016, 12, 01));
		flightSearch.setFlightclass(FlightClass.ECONOMY);
		fsbean.setFlight(flightSearch);
		List<Flight> flights = fsbean.getFlightsUsingCriteria();
		assertNotNull(flights);
		Iterator<Flight> it = flights.iterator();
		Boolean found = false;
		while (it.hasNext()) {
			if (it.next().getId().equals(flight.getId())) {
				found = true;
			}
		}
		assertEquals(true, found);

		flightSearch.setAirlineCompany("airliner");
		fsbean.setFlight(flightSearch);
		flights = fsbean.getFlightsUsingCriteria();
		assertNotNull(flights);
		it = flights.iterator();
		found = false;
		while (it.hasNext()) {
			if (it.next().getId().equals(flight.getId())) {
				found = true;
			}
		}
		assertEquals(true, found);

		flightSearch.setDeparture(departure);
		flightSearch.setSetAirport(1);
		fsbean.setFlight(flightSearch);
		flights = fsbean.getFlightsUsingCriteria();
		assertNotNull(flights);
		it = flights.iterator();
		found = false;
		while (it.hasNext()) {
			if (it.next().getId().equals(flight.getId())) {
				found = true;
			}
		}
		assertEquals(true, found);

		flightSearch.setDestination(destination);
		fsbean.setFlight(flightSearch);
		flights = fsbean.getFlightsUsingCriteria();
		assertNotNull(flights);
		it = flights.iterator();
		found = false;
		while (it.hasNext()) {
			if (it.next().getId().equals(flight.getId())) {
				found = true;
			}
		}
		assertEquals(true, found);

		flightSearch.setDateReturn(new Date(3000, 01, 01, 05, 12));
		fsbean.setFlight(flightSearch);
		flights = fsbean.getFlightsUsingCriteria();
		assertNotNull(flights);
		it = flights.iterator();
		found = false;
		while (it.hasNext()) {
			if (it.next().getId().equals(flight.getId())) {
				found = true;
			}
		}
		assertEquals(true, found);

		flightSearch.setDateReturn(new Date(3000, 01, 01, 05, 12));
		fsbean.setFlight(flightSearch);
		flights = fsbean.getFlightsUsingCriteria();
		assertNotNull(flights);
		it = flights.iterator();
		found = false;
		while (it.hasNext()) {
			if (it.next().getId().equals(flight.getId())) {
				found = true;
			}
		}
		assertEquals(true, found);

		flightSearch.setSetAirport(2);
		flightSearch.setRegion(GlobalRegion.WESTERN_EUROPE);
		fsbean.setFlight(flightSearch);
		flights = fsbean.getFlightsUsingCriteria();
		assertNotNull(flights);
		it = flights.iterator();
		found = false;
		while (it.hasNext()) {
			if (it.next().getId().equals(flight.getId())) {
				found = true;
			}
		}
		assertEquals(true, found);

		flightSearch.setAirlineCompany(null);
		;
		fsbean.setFlight(flightSearch);
		flights = fsbean.getFlightsUsingCriteria();
		assertNotNull(flights);
		it = flights.iterator();
		found = false;
		while (it.hasNext()) {
			if (it.next().getId().equals(flight.getId())) {
				found = true;
			}
		}
		assertEquals(true, found);

	}

	@Test(expected = LackingSearchCriteriaException.class)
	public void LackingSeatsFlightSearchCriteria() throws LackingSearchCriteriaException {
		FlightSearchBean fsbean = new FlightSearchBean();
		fsbean.setEm(entityManager());
		FlightSearch flightSearch = new FlightSearch();
		flightSearch.setDateDeparture(new Date(2016, 12, 01));
		flightSearch.setFlightclass(FlightClass.ECONOMY);
		fsbean.setFlight(flightSearch);
		fsbean.getFlightsUsingCriteria();
	}

	@Test(expected = LackingSearchCriteriaException.class)
	public void LackingDepartureDateFlightSearchCriteria() throws LackingSearchCriteriaException {
		FlightSearchBean fsbean = new FlightSearchBean();
		fsbean.setEm(entityManager());
		FlightSearch flightSearch = new FlightSearch();
		flightSearch.setSeats(2);
		flightSearch.setFlightclass(FlightClass.ECONOMY);
		fsbean.setFlight(flightSearch);
		fsbean.getFlightsUsingCriteria();
	}

	@Test(expected = LackingSearchCriteriaException.class)
	public void LackingFlightClassFlightSearchCriteria() throws LackingSearchCriteriaException {
		FlightSearchBean fsbean = new FlightSearchBean();
		fsbean.setEm(entityManager());
		FlightSearch flightSearch = new FlightSearch();
		flightSearch.setSeats(2);
		flightSearch.setDateDeparture(new Date(2016, 12, 01));
		fsbean.setFlight(flightSearch);
		fsbean.getFlightsUsingCriteria();
	}

}
