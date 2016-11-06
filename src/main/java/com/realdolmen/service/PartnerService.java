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
//@EJB(name="java:global/RAir/PartnerService", beanInterface = SessionRemote.class, beanName="PartnerService")
public class PartnerService implements SessionRemote {
	private Partner partner;
	
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
		System.err.println("obtaining flights");
		System.out.println(partner.getCompany());
		if(em==null){
			System.out.println("the fricking entity manager is still empty ... sigh");
		} else {
			System.out.println("the em isn't empty ... alright, finally");
		}
		System.err.println("em should work");
		List<Flight> flights =  em.createQuery("select f from Flight f where f.company = :company", Flight.class).setParameter("company", partner.getCompany()).getResultList();
		System.err.println("obtained flights");
		if(flights == null) {
			System.err.println("flights is null");
			return new ArrayList<Flight>();
		} else if (flights.isEmpty()) {
			System.err.println("flights is empty");
			return new ArrayList<Flight>();
		} else {
			System.err.println("it should work");
			return flights;
		}
	}
	
	@Override
	public Flight getFlight(Long id) throws AccessRightsException {
		Flight flight = em.createQuery("select f from Flight f where f.id = :id", Flight.class).setParameter("id", id).getSingleResult();
		if(flight.getCompany().equals(partner.getCompany())) {
			return flight;
		}
		throw new AccessRightsException("This flight does not belong to your company");		 
	}
}
