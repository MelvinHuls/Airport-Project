package com.realdolmen.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import com.realdolmen.Exceptions.AccessRightsException;
import com.realdolmen.domain.Flight;
import com.realdolmen.domain.Location;
import com.realdolmen.domain.Partner;

@Stateless
public class FlightRepository {

	@PersistenceContext
	EntityManager em;

	public void create(Flight o) {
		em.persist(o);
	}

	public Flight read(Long id) {
		return em.find(Flight.class, id);
	}

	public void update(Flight o) {
		em.merge(o);
	}

	public void delete(Flight o) {
		em.remove(o);
	}

	public List<Flight> findAll() {
		try {
			return em.createNamedQuery("SELECT f FROM Flight f", Flight.class).getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}

	public String changeFlight(Flight flight, Partner partner) throws AccessRightsException {
		flight.setDeparture(em
				.createQuery("select l from Location l where l.country = :country and l.airport = :airport",
						Location.class)
				.setParameter("country", flight.getDeparture().getCountry())
				.setParameter("airport", flight.getDeparture().getAirport()).getSingleResult());
		flight.setDestination(em
				.createQuery("select l from Location l where l.country = :country and l.airport = :airport",
						Location.class)
				.setParameter("country", flight.getDestination().getCountry())
				.setParameter("airport", flight.getDestination().getAirport()).getSingleResult());
		if (flight.getCompany().equals(partner.getCompany())) {
			em.merge(flight);
			flight = null;
			return "success";
		}
		throw new AccessRightsException("This flight does not belong to your company");
	}

	public String addFlight(Flight flight, Partner partner) {

		flight.setDeparture(em
				.createQuery("select l from Location l where l.country = :country and l.airport = :airport",
						Location.class)
				.setParameter("country", flight.getDeparture().getCountry())
				.setParameter("airport", flight.getDeparture().getAirport()).getSingleResult());
		flight.setDestination(em
				.createQuery("select l from Location l where l.country = :country and l.airport = :airport",
						Location.class)
				.setParameter("country", flight.getDestination().getCountry())
				.setParameter("airport", flight.getDestination().getAirport()).getSingleResult());
		flight.setCompany(partner.getCompany());

		em.persist(flight);
		return "success";
	}

	public Flight getFlight(Partner partner, Long id) throws AccessRightsException {
		System.out.println(id);
		Flight flight = em.createQuery("select f from Flight f where f.id = :id", Flight.class).setParameter("id", id)
				.getSingleResult();
		if (flight.getCompany().equals(partner.getCompany())) {
			return flight;
		}
		throw new AccessRightsException("This flight does not belong to your company");
	}

	public List<Flight> getFlightsByCompany(String company, Partner partner) {
		List<Flight> flights = em.createQuery("select f from Flight f where f.company = :company", Flight.class)
				.setParameter("company", partner.getCompany()).getResultList();
		if (flights == null || flights.isEmpty()) {
			return new ArrayList<Flight>();
		} else {
			return flights;
		}
	}
}