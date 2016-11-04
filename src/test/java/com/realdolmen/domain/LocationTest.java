package com.realdolmen.domain;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.BeforeClass;
import org.junit.Test;

import com.realdolmen.utilities.persistence.JpaPersistenceTest;

public class LocationTest extends JpaPersistenceTest {
	private EntityManagerFactory entityManagerFactory;
	@BeforeClass
	public void setUp(){
		entityManagerFactory = Persistence.createEntityManagerFactory("theFactory");		
	}
	@Test
	public void makingAndRetrievinglocation() throws Exception {
		Location location = new Location();
		assertNotNull(location);
		EntityManager em = entityManagerFactory.createEntityManager();
		em.persist(location);
		assertNotNull(em.find(Location.class, location.getId()));
	}
}
