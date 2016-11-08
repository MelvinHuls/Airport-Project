package com.realdolmen.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.realdolmen.Exceptions.AccessRightsException;
import com.realdolmen.domain.Flight;
import com.realdolmen.domain.Location;
import com.realdolmen.domain.Partner;
import com.realdolmen.repository.PartnerRepository;

@Stateful
@LocalBean
// @EJB(name="java:global/RAir/PartnerService", beanInterface =
// SessionRemote.class, beanName="PartnerService")
public class PartnerService implements SessionRemote {
	private Partner partner;

	private Flight flight;

	@PersistenceContext
	private EntityManager em;

	private PartnerRepository pRepo;

	public void create(Partner partner) {
		pRepo.create(partner);
	}

	public Partner read(Long id) {
		return pRepo.read(id);
	}

	public void update(Partner partner) {
		pRepo.update(partner);
	}

	public void delete(Partner partner) {
		pRepo.delete(partner);
	}

	protected PartnerService() {
	}

	public PartnerService(Partner partner) {
		super();
		this.partner = partner;
	}

	public void testFunctionality() {
		partner.changeDepartureTime();
	}

	public Partner getPartner() {
		return partner;
	}

	public void setPartner(Partner partner) {
		this.partner = partner;
	}

	@Override
	public List<Flight> obtainFlights() {
		List<Flight> flights = em.createQuery("select f from Flight f where f.company = :company", Flight.class)
				.setParameter("company", partner.getCompany()).getResultList();

		if (flights == null || flights.isEmpty()) {
			return new ArrayList<Flight>();
		} else {
			return flights;
		}
	}

	@Override
	public Flight getFlight(Long id) throws AccessRightsException {
		System.out.println(id);
		Flight flight = em.createQuery("select f from Flight f where f.id = :id", Flight.class).setParameter("id", id)
				.getSingleResult();
		if (flight.getCompany().equals(partner.getCompany())) {
			return flight;
		}
		throw new AccessRightsException("This flight does not belong to your company");
	}

	@Override
	public String changeFlight() throws AccessRightsException {
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
			em.merge(this.flight);
			this.flight = null;
			return "success";
		}

		throw new AccessRightsException("This flight does not belong to your company");
	}

	public Flight getFlight() {
		return flight;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}

	public String storeFlight(Flight flight) {
		this.flight = flight;
		return "edit";
	}

	public String newFlight() {
		this.flight = new Flight();
		flight.setDeparture(new Location());
		flight.setDestination(new Location());
		System.out.println("new flight");
		return "add";
	}

	public String addFlight() {
		System.out.println("adding flight");
		if (this.flight == null) {
			System.out.println("the flight is null");

			return "failed";
		}

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

		em.persist(this.flight);
		return "success";
	}

	public List<String> getCountries() {
		return em.createQuery("select distinct c.country from Location c", String.class).getResultList();
	}

	public List<String> getAirportsCountry(String country) {
		return em.createQuery("select distinct c.airport from Location c where c.country = :country", String.class)
				.setParameter("country", country).getResultList();
	}

	public String deleteFlight() {
		em.remove(em.find(Flight.class, flight.getId()));
		return "deleted";
	}
}
