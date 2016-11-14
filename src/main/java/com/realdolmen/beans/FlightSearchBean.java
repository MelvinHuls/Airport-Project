package com.realdolmen.beans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.realdolmen.Exceptions.LackingPricingInformationException;
import com.realdolmen.Exceptions.LackingSearchCriteriaException;
import com.realdolmen.domain.Flight;
import com.realdolmen.domain.FlightSearch;
import com.realdolmen.domain.Location;
import com.realdolmen.enumerations.FlightClass;
import com.realdolmen.enumerations.GlobalRegion;

@Stateless
@SessionScoped
@Remote
@ManagedBean(name = "flightSearchBean")
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

	private List<Flight> portCompDestCriteriaQuery(Date dateDeparture, Location departure, Location destination)
			throws LackingSearchCriteriaException {
		String seatsName;
		String classQuery;
		if (flight.getFlightclass().equals(FlightClass.ECONOMY)) {
			seatsName = "seatsEconomy";
			classQuery = "f.seatsEconomy>=:seatsEconomy";
		} else if (flight.getFlightclass().equals(FlightClass.BUSINESS)) {
			seatsName = "seatsBusiness";
			classQuery = "f.seatsBusiness>=:seatsBusiness";
		} else if (flight.getFlightclass().equals(FlightClass.FIRST_CLASS)) {
			seatsName = "seatsFirstClass";
			classQuery = "f.seatsFirstClass>=:seatsFirstClass";
		} else {
			throw new LackingSearchCriteriaException("The flight class is not filled in properly");
		}
		return em
				.createQuery("select f from Flight as f, Location as dep, Location as dest where " + classQuery
						+ " and dep.id=f.departure and dest.id=f.destination"
						+ /* id */" and f.departureTime > :departureDate and f.departureTime < :departureDateEndDay "
						+ "and dep.country=:depcountryairport and dep.airport=:depairport "
						+ "and dest.country=:destcountryairport and dest.airport=:destairport "
						+ "and f.company=:airlineCompany", Flight.class)
				.setParameter(seatsName, this.flight.getSeats()).setParameter("departureDate", dateDeparture)
				.setParameter("departureDateEndDay", new Date(dateDeparture.getTime() + 86400000))
				.setParameter("depcountryairport", departure.getCountry())
				.setParameter("depairport", departure.getAirport())
				.setParameter("destcountryairport", destination.getCountry())
				.setParameter("destairport", destination.getAirport())
				.setParameter("airlineCompany", this.flight.getAirlineCompany()).getResultList();
	}

	private List<Flight> portDestCriteriaQuery(Date dateDeparture, Location departure, Location destination)
			throws LackingSearchCriteriaException {
		String seatsName;
		String classQuery;
		if (flight.getFlightclass().equals(FlightClass.ECONOMY)) {
			seatsName = "seatsEconomy";
			classQuery = "f.seatsEconomy>=:seatsEconomy";
		} else if (flight.getFlightclass().equals(FlightClass.BUSINESS)) {
			seatsName = "seatsBusiness";
			classQuery = "f.seatsBusiness>=:seatsBusiness";
		} else if (flight.getFlightclass().equals(FlightClass.FIRST_CLASS)) {
			seatsName = "seatsFirstClass";
			classQuery = "f.seatsFirstClass>=:seatsFirstClass";
		} else {
			throw new LackingSearchCriteriaException("The flight class is not filled in properly");
		}
		return em
				.createQuery("select f from Flight as f, Location as dep, Location as dest where " + classQuery
						+ " and dep.id=f.departure and dest.id=f.destination"
						+ /* id */" and f.departureTime > :departureDate and f.departureTime < :departureDateEndDay "
						+ "and dep.country=:depcountryairport and dep.airport=:depairport "
						+ "and dest.country=:destcountryairport and dest.airport=:destairport ", Flight.class)
				.setParameter(seatsName, this.flight.getSeats()).setParameter("departureDate", dateDeparture)
				.setParameter("departureDateEndDay", new Date(dateDeparture.getTime() + 86400000))
				.setParameter("depcountryairport", departure.getCountry())
				.setParameter("depairport", departure.getAirport())
				.setParameter("destcountryairport", destination.getCountry())
				.setParameter("destairport", destination.getAirport()).getResultList();
	}

	private List<Flight> portCompCriteriaQuery(Date dateDeparture, Location departure)
			throws LackingSearchCriteriaException {
		String seatsName;
		String classQuery;
		if (flight.getFlightclass().equals(FlightClass.ECONOMY)) {
			seatsName = "seatsEconomy";
			classQuery = "f.seatsEconomy>=:seatsEconomy";
		} else if (flight.getFlightclass().equals(FlightClass.BUSINESS)) {
			seatsName = "seatsBusiness";
			classQuery = "f.seatsBusiness>=:seatsBusiness";
		} else if (flight.getFlightclass().equals(FlightClass.FIRST_CLASS)) {
			seatsName = "seatsFirstClass";
			classQuery = "f.seatsFirstClass>=:seatsFirstClass";
		} else {
			throw new LackingSearchCriteriaException("The flight class is not filled in properly");
		}
		return em.createQuery(
				"select f from Flight as f, Location as dep where " + classQuery + " and dep.id=f.departure "
						+ /* id */" and f.departureTime > :departureDate and f.departureTime < :departureDateEndDay "
						+ "and dep.country=:countryairport and dep.airport=:airport " + "and f.company=:airlineCompany",
				Flight.class).setParameter(seatsName, this.flight.getSeats())
				.setParameter("departureDate", dateDeparture)
				.setParameter("departureDateEndDay", new Date(dateDeparture.getTime() + 86400000))
				.setParameter("countryairport", departure.getCountry()).setParameter("airport", departure.getAirport())
				.setParameter("airlineCompany", this.flight.getAirlineCompany()).getResultList();
	}

	private List<Flight> portCriteriaQuery(Date dateDeparture, Location departure)
			throws LackingSearchCriteriaException {
		String seatsName;
		String classQuery;
		if (flight.getFlightclass().equals(FlightClass.ECONOMY)) {
			seatsName = "seatsEconomy";
			classQuery = "f.seatsEconomy>=:seatsEconomy";
		} else if (flight.getFlightclass().equals(FlightClass.BUSINESS)) {
			seatsName = "seatsBusiness";
			classQuery = "f.seatsBusiness>=:seatsBusiness";
		} else if (flight.getFlightclass().equals(FlightClass.FIRST_CLASS)) {
			seatsName = "seatsFirstClass";
			classQuery = "f.seatsFirstClass>=:seatsFirstClass";
		} else {
			throw new LackingSearchCriteriaException("The flight class is not filled in properly");
		}
		return em
				.createQuery(
						"select f from Flight as f, Location as dep where " + classQuery + " and dep.id=f.departure"
								+ /* id */" and f.departureTime > :departureDate and f.departureTime < :departureDateEndDay "
								+ "and dep.country=:countryairport and dep.airport=:airport ",
						Flight.class)
				.setParameter(seatsName, this.flight.getSeats()).setParameter("departureDate", dateDeparture)
				.setParameter("departureDateEndDay", new Date(dateDeparture.getTime() + 86400000))
				.setParameter("countryairport", departure.getCountry()).setParameter("airport", departure.getAirport())
				.getResultList();
	}

	private List<Flight> regionCompCriteriaQuery(Date dateDeparture, GlobalRegion region)
			throws LackingSearchCriteriaException {
		String seatsName;
		String classQuery;
		if (flight.getFlightclass().equals(FlightClass.ECONOMY)) {
			seatsName = "seatsEconomy";
			classQuery = "f.seatsEconomy>=:seatsEconomy";
		} else if (flight.getFlightclass().equals(FlightClass.BUSINESS)) {
			seatsName = "seatsBusiness";
			classQuery = "f.seatsBusiness>=:seatsBusiness";
		} else if (flight.getFlightclass().equals(FlightClass.FIRST_CLASS)) {
			seatsName = "seatsFirstClass";
			classQuery = "f.seatsFirstClass>=:seatsFirstClass";
		} else {
			throw new LackingSearchCriteriaException("The flight class is not filled in properly");
		}
		return em
				.createQuery(
						"select f from Flight as f, Location as dep where " + classQuery + " and dep.id=f.departure"
								+ /* id */" and f.departureTime > :departureDate and f.departureTime < :departureDateEndDay "
								+ "and dep.region=:region " + "and f.company=:airlineCompany",
						Flight.class)
				.setParameter(seatsName, this.flight.getSeats()).setParameter("departureDate", dateDeparture)
				.setParameter("departureDateEndDay", new Date(dateDeparture.getTime() + 86400000))
				.setParameter("region", region).setParameter("airlineCompany", this.flight.getAirlineCompany())
				.getResultList();
	}

	private List<Flight> minCompCriteriaQuery(Date dateDeparture) throws LackingSearchCriteriaException {
		String seatsName;
		String classQuery;
		if (flight.getFlightclass().equals(FlightClass.ECONOMY)) {
			seatsName = "seatsEconomy";
			classQuery = "f.seatsEconomy>=:seatsEconomy";
		} else if (flight.getFlightclass().equals(FlightClass.BUSINESS)) {
			seatsName = "seatsBusiness";
			classQuery = "f.seatsBusiness>=:seatsBusiness";
		} else if (flight.getFlightclass().equals(FlightClass.FIRST_CLASS)) {
			seatsName = "seatsFirstClass";
			classQuery = "f.seatsFirstClass>=:seatsFirstClass";
		} else {
			throw new LackingSearchCriteriaException("The flight class is not filled in properly");
		}
		return em
				.createQuery(
						"select f from Flight as f, Location as dep where " + classQuery + " and dep.id=f.departure"
								+ /* id */" and f.departureTime > :departureDate and f.departureTime < :departureDateEndDay "
								+ "and f.company=:airlineCompany",
						Flight.class)
				.setParameter(seatsName, this.flight.getSeats()).setParameter("departureDate", dateDeparture)
				.setParameter("departureDateEndDay", new Date(dateDeparture.getTime() + 86400000))
				.setParameter("airlineCompany", this.flight.getAirlineCompany()).getResultList();
	}

	private List<Flight> minimalCriteriaQuery(Date dateDeparture) throws LackingSearchCriteriaException {
		String seatsName;
		String classQuery;
		if (flight.getFlightclass().equals(FlightClass.ECONOMY)) {
			seatsName = "seatsEconomy";
			classQuery = "f.seatsEconomy>=:seatsEconomy";
		} else if (flight.getFlightclass().equals(FlightClass.BUSINESS)) {
			seatsName = "seatsBusiness";
			classQuery = "f.seatsBusiness>=:seatsBusiness";
		} else if (flight.getFlightclass().equals(FlightClass.FIRST_CLASS)) {
			seatsName = "seatsFirstClass";
			classQuery = "f.seatsFirstClass>=:seatsFirstClass";
		} else {
			throw new LackingSearchCriteriaException("The flight class is not filled in properly");
		}
		return em
				.createQuery(
						"select f from Flight as f, Location as dep where " + classQuery + " and dep.id=f.departure"
								+ /* id */" and f.departureTime > :departureDate and f.departureTime < :departureDateEndDay ",
						Flight.class)
				.setParameter(seatsName, this.flight.getSeats()).setParameter("departureDate", dateDeparture)
				.setParameter("departureDateEndDay", new Date(dateDeparture.getTime() + 86400000)).getResultList();
	}

	private List<Flight> regionCriteriaQuery(Date dateDeparture, GlobalRegion region)
			throws LackingSearchCriteriaException {
		String seatsName;
		String classQuery;
		if (flight.getFlightclass().equals(FlightClass.ECONOMY)) {
			seatsName = "seatsEconomy";
			classQuery = "f.seatsEconomy>=:seatsEconomy";
		} else if (flight.getFlightclass().equals(FlightClass.BUSINESS)) {
			seatsName = "seatsBusiness";
			classQuery = "f.seatsBusiness>=:seatsBusiness";
		} else if (flight.getFlightclass().equals(FlightClass.FIRST_CLASS)) {
			seatsName = "seatsFirstClass";
			classQuery = "f.seatsFirstClass>=:seatsFirstClass";
		} else {
			throw new LackingSearchCriteriaException("The flight class is not filled in properly");
		}
		return em
				.createQuery(
						"select f from Flight as f, Location as dep where " + classQuery + " and dep.id=f.departure"
								+ /* id */" and f.departureTime > :departureDate and f.departureTime < :departureDateEndDay "
								+ "and dep.region=:region",
						Flight.class)
				.setParameter(seatsName, this.flight.getSeats()).setParameter("departureDate", dateDeparture)
				.setParameter("departureDateEndDay", new Date(dateDeparture.getTime() + 86400000))
				.setParameter("region", region).getResultList();
	}

	public List<Flight> getFlightsUsingCriteria() throws LackingSearchCriteriaException {
		if (flight.getFlightclass() != null && flight.getSeats() != null && flight.getDateDeparture() != null) {
			if (flight.getSetAirport() == 1 && flight.getDeparture().getCountry() != null
					&& flight.getDeparture().getAirport() != null && !flight.getDeparture().getCountry().equals("")
					&& !flight.getDeparture().getAirport().equals("")) {
				if (flight.getDestination().getAirport() != null && !flight.getDestination().getCountry().equals("")
						&& !flight.getDestination().getAirport().equals("")) {
					if (flight.getAirlineCompany() != null && !flight.getAirlineCompany().equals("")) {
						return portCompDestCriteriaQuery(this.flight.getDateDeparture(), this.flight.getDeparture(),
								this.flight.getDestination());
					} else {
						return portDestCriteriaQuery(this.flight.getDateDeparture(), this.flight.getDeparture(),
								this.flight.getDestination());
					}
				} else if (flight.getAirlineCompany() != null && !flight.getAirlineCompany().equals("")) {
					return portCompCriteriaQuery(this.flight.getDateDeparture(), this.flight.getDeparture());
				} else {
					return portCriteriaQuery(this.flight.getDateDeparture(), this.flight.getDestination());
				}
			} else if (flight.getSetAirport() == 2 && flight.getRegion() != null) {
				if (flight.getAirlineCompany() != null && !flight.getAirlineCompany().equals("")) {
					return regionCompCriteriaQuery(this.flight.getDateDeparture(), this.flight.getRegion());
				}
				return regionCriteriaQuery(this.flight.getDateDeparture(), this.flight.getRegion());
			} else if (flight.getSetAirport() == 0) {
				if (flight.getAirlineCompany() != null && !flight.getAirlineCompany().equals("")) {
					return minCompCriteriaQuery(this.flight.getDateDeparture());
				}
				return minimalCriteriaQuery(this.flight.getDateDeparture());
			}
		}

		throw new LackingSearchCriteriaException("The search criteria were not properly filled in");
	}

	public Double getPriceFlightClass(Flight f) throws LackingSearchCriteriaException {
		if (flight.getFlightclass() == FlightClass.ECONOMY) {
			return f.getPriceEconomy();
		} else if (flight.getFlightclass() == FlightClass.BUSINESS) {
			return f.getPriceBusiness();
		} else if (flight.getFlightclass() == FlightClass.FIRST_CLASS) {
			return f.getPriceFirstClass();
		} else {
			throw new LackingSearchCriteriaException("The flight class is not filled in properly");
		}
	}

	public Double calculateTotalPrice(Flight actualFlight) throws LackingPricingInformationException {
		return actualFlight.calculateTotalPriceClass(flight.getFlightclass(), flight.getSeats());
	}

	public Double calculateDiscount(Flight actualFlight) {
		return actualFlight.calculateDiscountClass(flight.getFlightclass(), flight.getSeats());
	}

	public List<Flight> getReturnFlights(Flight outwardFlight) throws LackingSearchCriteriaException {
		/*
		 * flight.getSeats() flight.getDestination() =
		 * outwardFlight.getDestination() flight.getDateDeparture() =
		 * flight.getDateReturn() flight.getFlightclass()
		 */
		if (flight.getDateReturn() != null) {
			if (flight.getFlightclass() != null && flight.getSeats() != null) {
				if (flight.getSetAirport() == 1 && outwardFlight.getDestination().getCountry() != null
						&& outwardFlight.getDestination().getAirport() != null
						&& !outwardFlight.getDestination().getCountry().equals("")
						&& !outwardFlight.getDestination().getAirport().equals("")) {
					if (flight.getDeparture().getAirport() != null && !flight.getDeparture().getCountry().equals("")
							&& !flight.getDeparture().getAirport().equals("")) {
						if (flight.getAirlineCompany() != null && !flight.getAirlineCompany().equals("")) {
							return portCompDestCriteriaQuery(this.flight.getDateReturn(),
									outwardFlight.getDestination(), this.flight.getDeparture());
						} else {
							return portDestCriteriaQuery(this.flight.getDateReturn(), outwardFlight.getDestination(),
									this.flight.getDeparture());
						}
					} else if (flight.getAirlineCompany() != null && !flight.getAirlineCompany().equals("")) {
						return portCompCriteriaQuery(this.flight.getDateReturn(), outwardFlight.getDestination());
					} else {
						return portCriteriaQuery(this.flight.getDateReturn(), outwardFlight.getDestination());
					}
				} else if (flight.getSetAirport() == 2 && flight.getRegion() != null) {
					if (flight.getAirlineCompany() != null && !flight.getAirlineCompany().equals("")) {
						return regionCompCriteriaQuery(this.flight.getDateReturn(),
								outwardFlight.getDestination().getRegion());
					}
					return regionCriteriaQuery(this.flight.getDateReturn(), outwardFlight.getDestination().getRegion());
				} else if (flight.getSetAirport() == 0) {
					if (flight.getAirlineCompany() != null && !flight.getAirlineCompany().equals("")) {
						return minCompCriteriaQuery(flight.getDateReturn());
					}
					return minimalCriteriaQuery(flight.getDateReturn());
				}
			}
			throw new LackingSearchCriteriaException("The search criteria were not properly filled in");
		}
		return new ArrayList<Flight>();
	}
	
	public Boolean returnFlightPlanned() {
		return (this.flight.getDateReturn() != null);
	}
}
