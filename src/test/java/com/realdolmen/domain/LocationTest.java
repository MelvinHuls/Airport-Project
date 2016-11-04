package com.realdolmen.domain;

import javax.persistence.EntityManager;

import org.junit.Test;

import com.realdolmen.utilities.persistence.JpaPersistenceTest;

public class LocationTest extends JpaPersistenceTest {

	@Test
	public void makingAndRetrievinglocation() throws Exception {
		Location location = new Location();
		assertNotNull(location);
		EntityManager em = entityManager();
		em.persist(location);
		assertNotNull(em.find(Location.class, location.getId()));
	}
}
