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
public class PartnerRepository {

	@PersistenceContext
	EntityManager em;

	/*
	 * List<Flight> flights = em.createQuery(
	 * "select f from Flight f where f.company = :company", Flight.class)
	 * .setParameter("company", partner.getCompany()).getResultList();
	 */

	public void create(Partner o) {
		em.persist(o);
	}

	public Partner read(Long id) {
		return em.find(Partner.class, id);
	}

	public void update(Partner o) {
		em.merge(o);
	}

	public void delete(Partner o) {
		em.remove(o);
	}

	public List<Partner> findAll() {
		try {
			return em.createNamedQuery("SELECT p FROM Partner p", Partner.class).getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
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
}