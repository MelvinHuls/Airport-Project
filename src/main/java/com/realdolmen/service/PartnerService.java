package com.realdolmen.service;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.realdolmen.Exceptions.AccessRightsException;
import com.realdolmen.domain.Flight;
import com.realdolmen.domain.Location;
import com.realdolmen.domain.Partner;
import com.realdolmen.repository.FlightRepository;
import com.realdolmen.repository.LocationRepository;
import com.realdolmen.repository.PartnerRepository;

@Stateful
@LocalBean
// @EJB(name="java:global/RAir/PartnerService", beanInterface =
// SessionRemote.class, beanName="PartnerService")
public class PartnerService implements SessionRemote, AbstractService<Partner> {
	private Partner partner;

	private Flight flight;

	@PersistenceContext
	private EntityManager em;

	@RequestScoped
	private PartnerRepository pRepo;
	@RequestScoped
	private FlightRepository fRepo;
	@RequestScoped
	private LocationRepository lRepo;

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
		partner = new Partner("Gerald", "123", "Gerald@airliner.com", "airliner");
		return fRepo.getFlightsByCompany(partner.getCompany(), partner);
	}

	@Override
	public Flight getFlight(Long id) throws AccessRightsException {
		return fRepo.getFlight(partner, id);
	}

	@Override
	public String changeFlight() throws AccessRightsException {

		return fRepo.changeFlight(flight, partner);
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
		return fRepo.addFlight(flight, partner);
	}

	public List<String> getCountries() {
		return lRepo.getCountries();
	}

	public List<String> getAirportsCountry(String country) {
		return lRepo.getAirportsCountry(country);
	}

	public String deleteFlight() {
		em.remove(em.find(Flight.class, flight.getId()));
		return "deleted";
	}
}
