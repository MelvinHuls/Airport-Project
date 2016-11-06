package com.realdolmen.service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.realdolmen.Exceptions.AccessRightsException;
import com.realdolmen.domain.Flight;
import com.realdolmen.domain.Partner;

@Stateful
@LocalBean
// @EJB(name="java:global/RAir/PartnerService", beanInterface =
// SessionRemote.class, beanName="PartnerService")
public class PartnerService implements SessionRemote {
	private Partner partner;

	private Flight flight;

	@PersistenceContext
	private EntityManager em;

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
		System.out.println("test");
		System.out.println(this.flight.getId());
		System.out.println("test");
		if (flight.getCompany().equals(partner.getCompany())) {
			em.merge(this.flight);
			this.flight = null;
			return "success";
		}

		throw new AccessRightsException("This flight does not belong to your company");
	}

	public Flight getFlight() {
		System.out.println("test");
		return flight;
	}

	public void setFlight(Flight flight) {
		System.out.println("flightset");
		this.flight = flight;
	}
	
	public String storeFlight(Flight flight) {
		System.out.println("flightset");
		this.flight = flight;
		return "edit";
	}
}
