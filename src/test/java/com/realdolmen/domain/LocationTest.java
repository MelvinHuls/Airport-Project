package com.realdolmen.domain;

import org.junit.Before;
import org.junit.Test;

import com.realdolmen.utilities.persistence.JpaPersistenceTest;

public class LocationTest extends JpaPersistenceTest {

	private Location location;

	@Before
	public void setUp() {
		location = new Location();
	}

	@Test
	public void makingAndRetrievinglocation() throws Exception {
		assertNull(location.getId());
		entityManager().persist(location);
		assertNotNull(location.getId());
	}
}
