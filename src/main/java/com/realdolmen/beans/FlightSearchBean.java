package com.realdolmen.beans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.faces.bean.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.realdolmen.domain.Flight;
import com.realdolmen.domain.FlightSearch;
import com.realdolmen.domain.Location;
import com.realdolmen.enumerations.FlightClass;

@Stateless
@RequestScoped
@Remote
@Named("flightSearchBean")
public class FlightSearchBean {

	@PersistenceContext
	EntityManager em;
	
	private FlightSearch flight;

	@PostConstruct
	private void init() {
		flight = new FlightSearch();
		flight.setDeparture(new Location());
		flight.setDestination(new Location());
	}

	public FlightSearch getFlight() {
		return flight;
	}

	public void setFlight(FlightSearch flight) {
		this.flight = flight;
	}

	public List<Flight> getFlights() {
		List<Flight> flights = em.createQuery("select f from Flight f", Flight.class).getResultList();

		if (flights == null || flights.isEmpty()) {
			return new ArrayList<Flight>();
		} else {
			return flights;
		}
	}
	
	public List<FlightClass> getFlightClasses() {
		return Arrays.asList(FlightClass.values());
	}
	
	public List<String> getCompanies() {
		List<String> resultList = em.createQuery("select u.company from User u group by u.company order by u.company", String.class).getResultList();
		resultList.remove(null);
		return resultList;
	}
	
	public List<String> getCountries() {
		return em.createQuery("select distinct c.country from Location c", String.class).getResultList();
	}

	public List<String> getAirportsCountry(String country) {
		return em.createQuery("select distinct c.airport from Location c where c.country = :country", String.class)
				.setParameter("country", country).getResultList();
	}
}
