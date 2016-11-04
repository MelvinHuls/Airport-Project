package com.realdolmen.domain;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.BeforeClass;
import org.junit.Test;

import com.realdolmen.utilities.persistence.JpaPersistenceTest;

public class FlightTest extends JpaPersistenceTest {
	private EntityManagerFactory entityManagerFactory;
	@BeforeClass
	public void setUp(){
		entityManagerFactory = Persistence.createEntityManagerFactory("theFactory");		
	}
	@Test
	public void makingAndRetrievingFlight() throws Exception {
		Flight flight = new Flight();
		assertNotNull(flight);
		EntityManager em = entityManagerFactory.createEntityManager();
		em.persist(flight);
		assertNotNull(em.find(Flight.class, flight.getId()));
	}
}
