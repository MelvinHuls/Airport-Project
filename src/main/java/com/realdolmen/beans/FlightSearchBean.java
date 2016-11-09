package com.realdolmen.beans;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.faces.bean.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.realdolmen.Exceptions.LackingSearchCriteriaException;
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

	public List<Flight> getAllFlights() {
		List<Flight> flights = em.createQuery("select f from Flight as f", Flight.class).getResultList();

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
		List<String> resultList = em
				.createQuery("select u.company from User u group by u.company order by u.company", String.class)
				.getResultList();
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

	private List<Flight> portCompCriteriaQuery() throws LackingSearchCriteriaException {
		String seatsName;
		String classQuery;
		if (flight.getFlightclass().equals(FlightClass.Economy)) {
			seatsName = "seatsEconomy";
			classQuery = "f.seatsEconomy>=:seatsEconomy";
		} else if (flight.getFlightclass().equals(FlightClass.Business)) {
			seatsName = "seatsBusiness";
			classQuery = "f.seatsBusiness>=:seatsBusiness";
		} else if (flight.getFlightclass().equals(FlightClass.First_Class)) {
			seatsName = "seatsFirstClass";
			classQuery = "f.seatsFirstClass>=:seatsFirstClass";
		} else {
			throw new LackingSearchCriteriaException("The flight class is not filled in properly");
		}
		return em
				.createQuery("select f from Flight as f, Location as dep where " + classQuery
						+ " and dep.id=f.departure "+/*id*/" and f.departureTime > :departureDate and f.departureTime < :departureDateEndDay "
						+ "and dep.country=:countryairport and dep.airport=:airport " + "and f.company=:airlineCompany",
						Flight.class)
				.setParameter(seatsName, this.flight.getSeats())
				.setParameter("departureDate", this.flight.getDateDeparture())
				.setParameter("departureDateEndDay", new Date(this.flight.getDateDeparture().getTime() + 86400000))
				.setParameter("countryairport", this.flight.getDeparture().getCountry())
				.setParameter("airport", this.flight.getDeparture().getAirport())
				.setParameter("airlineCompany", this.flight.getAirlineCompany()).getResultList();
	}

	private List<Flight> portCriteriaQuery() throws LackingSearchCriteriaException {
		String seatsName;
		String classQuery;
		if (flight.getFlightclass().equals(FlightClass.Economy)) {
			seatsName = "seatsEconomy";
			classQuery = "f.seatsEconomy>=:seatsEconomy";
		} else if (flight.getFlightclass().equals(FlightClass.Business)) {
			seatsName = "seatsBusiness";
			classQuery = "f.seatsBusiness>=:seatsBusiness";
		} else if (flight.getFlightclass().equals(FlightClass.First_Class)) {
			seatsName = "seatsFirstClass";
			classQuery = "f.seatsFirstClass>=:seatsFirstClass";
		} else {
			throw new LackingSearchCriteriaException("The flight class is not filled in properly");
		}
		return em
				.createQuery("select f from Flight as f, Location as dep where " + classQuery
						+ " and dep.id=f.departure"+/*id*/" and f.departureTime > :departureDate and f.departureTime < :departureDateEndDay "
						+ "and dep.country=:countryairport and dep.airport=:airport ", Flight.class)
				.setParameter(seatsName, this.flight.getSeats())
				.setParameter("departureDate", this.flight.getDateDeparture())
				.setParameter("departureDateEndDay", new Date(this.flight.getDateDeparture().getTime() + 86400000))
				.setParameter("countryairport", this.flight.getDeparture().getCountry())
				.setParameter("airport", this.flight.getDeparture().getAirport()).getResultList();
	}

	private List<Flight> regionCompCriteriaQuery() throws LackingSearchCriteriaException {
		String seatsName;
		String classQuery;
		if (flight.getFlightclass().equals(FlightClass.Economy)) {
			seatsName = "seatsEconomy";
			classQuery = "f.seatsEconomy>=:seatsEconomy";
		} else if (flight.getFlightclass().equals(FlightClass.Business)) {
			seatsName = "seatsBusiness";
			classQuery = "f.seatsBusiness>=:seatsBusiness";
		} else if (flight.getFlightclass().equals(FlightClass.First_Class)) {
			seatsName = "seatsFirstClass";
			classQuery = "f.seatsFirstClass>=:seatsFirstClass";
		} else {
			throw new LackingSearchCriteriaException("The flight class is not filled in properly");
		}
		return em
				.createQuery("select f from Flight as f, Location as dep where " + classQuery
						+ " and dep.id=f.departure"+/*id*/" and f.departureTime > :departureDate and f.departureTime < :departureDateEndDay " + "and dep.region=:region "
						+ "and f.company=:airlineCompany", Flight.class)
				.setParameter(seatsName, this.flight.getSeats())
				.setParameter("departureDate", this.flight.getDateDeparture())
				.setParameter("departureDateEndDay", new Date(this.flight.getDateDeparture().getTime() + 86400000))
				.setParameter("region", this.flight.getRegion())
				.setParameter("airlineCompany", this.flight.getAirlineCompany()).getResultList();
	}

	private List<Flight> minCompCriteriaQuery() throws LackingSearchCriteriaException {
		String seatsName;
		String classQuery;
		if (flight.getFlightclass().equals(FlightClass.Economy)) {
			seatsName = "seatsEconomy";
			classQuery = "f.seatsEconomy>=:seatsEconomy";
		} else if (flight.getFlightclass().equals(FlightClass.Business)) {
			seatsName = "seatsBusiness";
			classQuery = "f.seatsBusiness>=:seatsBusiness";
		} else if (flight.getFlightclass().equals(FlightClass.First_Class)) {
			seatsName = "seatsFirstClass";
			classQuery = "f.seatsFirstClass>=:seatsFirstClass";
		} else {
			throw new LackingSearchCriteriaException("The flight class is not filled in properly");
		}
		return em
				.createQuery("select f from Flight as f, Location as dep where " + classQuery
						+ " and dep.id=f.departure"+/*id*/" and f.departureTime > :departureDate and f.departureTime < :departureDateEndDay " + "and f.company=:airlineCompany",
						Flight.class)
				.setParameter(seatsName, this.flight.getSeats())
				.setParameter("departureDate", this.flight.getDateDeparture())
				.setParameter("departureDateEndDay", new Date(this.flight.getDateDeparture().getTime() + 86400000))
				.setParameter("airlineCompany", this.flight.getAirlineCompany()).getResultList();
	}

	private List<Flight> minimalCriteriaQuery() throws LackingSearchCriteriaException {
		String seatsName;
		String classQuery;
		if (flight.getFlightclass().equals(FlightClass.Economy)) {
			seatsName = "seatsEconomy";
			classQuery = "f.seatsEconomy>=:seatsEconomy";
		} else if (flight.getFlightclass().equals(FlightClass.Business)) {
			seatsName = "seatsBusiness";
			classQuery = "f.seatsBusiness>=:seatsBusiness";
		} else if (flight.getFlightclass().equals(FlightClass.First_Class)) {
			seatsName = "seatsFirstClass";
			classQuery = "f.seatsFirstClass>=:seatsFirstClass";
		} else {
			throw new LackingSearchCriteriaException("The flight class is not filled in properly");
		}
		return em
				.createQuery("select f from Flight as f, Location as dep where " + classQuery
						+ " and dep.id=f.departure"+/*id*/" and f.departureTime > :departureDate and f.departureTime < :departureDateEndDay ", Flight.class)
				.setParameter(seatsName, this.flight.getSeats())
				.setParameter("departureDate", this.flight.getDateDeparture())
				.setParameter("departureDateEndDay", new Date(this.flight.getDateDeparture().getTime() + 86400000)).getResultList();
	}

	private List<Flight> regionCriteriaQuery() throws LackingSearchCriteriaException {
		String seatsName;
		String classQuery;
		if (flight.getFlightclass().equals(FlightClass.Economy)) {
			seatsName = "seatsEconomy";
			classQuery = "f.seatsEconomy>=:seatsEconomy";
		} else if (flight.getFlightclass().equals(FlightClass.Business)) {
			seatsName = "seatsBusiness";
			classQuery = "f.seatsBusiness>=:seatsBusiness";
		} else if (flight.getFlightclass().equals(FlightClass.First_Class)) {
			seatsName = "seatsFirstClass";
			classQuery = "f.seatsFirstClass>=:seatsFirstClass";
		} else {
			throw new LackingSearchCriteriaException("The flight class is not filled in properly");
		}
		return em
				.createQuery("select f from Flight as f, Location as dep where " + classQuery
						+ " and dep.id=f.departure"+/*id*/" and f.departureTime > :departureDate and f.departureTime < :departureDateEndDay " + "and dep.region=:region",
						Flight.class)
				.setParameter(seatsName, this.flight.getSeats())
				.setParameter("departureDate", this.flight.getDateDeparture())
				.setParameter("departureDateEndDay", new Date(this.flight.getDateDeparture().getTime() + 86400000))
				.setParameter("region", this.flight.getRegion()).getResultList();
	}

	public List<Flight> getFlightsUsingCriteria() throws LackingSearchCriteriaException {
		if (flight.getFlightclass() != null && flight.getSeats() != null && flight.getDateDeparture() != null) {
			if (flight.getSetAirport() == 1 && flight.getDeparture().getCountry() != null
					&& flight.getDeparture().getAirport() != null && !flight.getDeparture().getCountry().equals("")
					&& !flight.getDeparture().getAirport().equals("")) {
				if (flight.getAirlineCompany() != null && !flight.getAirlineCompany().equals("")) {
					return portCompCriteriaQuery();
				} else {
					return portCriteriaQuery();
				}
			} else if (flight.getSetAirport() == 2 && flight.getRegion() != null) {
				if (flight.getAirlineCompany() != null && !flight.getAirlineCompany().equals("")) {
					return regionCompCriteriaQuery();
				} else {
					return regionCriteriaQuery();
				}
			} else if (flight.getSetAirport() == 0) {
				if (flight.getAirlineCompany() != null && !flight.getAirlineCompany().equals("")) {
					return minCompCriteriaQuery();
				} else {
					return minimalCriteriaQuery();
				}
			}
		}

		throw new LackingSearchCriteriaException("The search criteria were not properly filled in");
	}

	public BigDecimal getPriceFlightClass(Flight f) throws LackingSearchCriteriaException {
		if (flight.getFlightclass() == FlightClass.Economy) {
			return f.getPriceEconomy();
		} else if (flight.getFlightclass() == FlightClass.Business) {
			return f.getPriceBusiness();
		} else if (flight.getFlightclass() == FlightClass.First_Class) {
			return f.getPriceFirstClass();
		} else {
			throw new LackingSearchCriteriaException("The flight class is not filled in properly");
		}
	}
}
