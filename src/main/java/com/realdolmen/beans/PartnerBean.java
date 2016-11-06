package com.realdolmen.beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.realdolmen.domain.Flight;

@Stateless
@LocalBean
public class PartnerBean {
	@PersistenceContext
	private EntityManager em;
	
	public List<Flight> obtainFlights(String company) {
		System.err.println("obtaining flights");
		System.out.println(company);
		if(em==null){
			System.out.println("the fricking entity manager is still empty ... sigh");
		} else {
			System.out.println("the em isn't empty ... alright, finally");
		}
		System.err.println("em should work");
		List<Flight> flights =  em.createQuery("select f from Flight f", Flight.class).getResultList();
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
	
}
