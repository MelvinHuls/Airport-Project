package com.realdolmen.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.realdolmen.Exceptions.AccessRightsException;
import com.realdolmen.domain.Flight;
import com.realdolmen.domain.Location;
import com.realdolmen.domain.Partner;
import com.realdolmen.repository.PartnerRepository;

@Stateless
@LocalBean
// @EJB(name="java:global/RAir/PartnerService", beanInterface =
// SessionRemote.class, beanName="PartnerService")
public class PartnerService implements SessionRemote, AbstractService<Partner> {
	private Partner partner;

	private Flight flight;
	
	private Boolean editFlight;

	@PersistenceContext
	private EntityManager em;

	@EJB
	private PartnerRepository pRepo;

	@Override
	public void create(Partner partner) {
		pRepo.create(partner);
	}

	@Override
	public Partner findById(Long id) {
		return pRepo.read(id);
	}

	@Override
	public void update(Partner partner) {
		pRepo.update(partner);
	}

	@Override
	public void delete(Partner partner) {
		pRepo.delete(partner);
	}

	@Override
	public List<Partner> findAll() {
		return pRepo.findAll();
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
		return pRepo.getFlightsByCompany(partner.getCompany(), partner);
	}

	@Override
	public Flight getFlight(Long id) throws AccessRightsException {
		return pRepo.getFlight(partner, id);
	}

	@Override
	public String changeFlight() throws AccessRightsException {

		return pRepo.changeFlight(flight, partner);
	}

	public Flight getFlight() {
		return flight;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}

	public String editFlight(Flight flight) {
		this.flight = flight;
		this.editFlight = true;
		return "edit";
	}

	public String newFlight() {
		this.flight = new Flight();
		flight.setDeparture(new Location());
		flight.setDestination(new Location());
		editFlight = false;
		System.out.println("new flight");
		return "edit";
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

	public Boolean getEditFlight() {
		return editFlight;
	}

	public void setEditFlight(Boolean editFlight) {
		this.editFlight = editFlight;
	}
	
	
}
