package com.realdolmen.domain;

import org.junit.Test;

import com.realdolmen.enumerations.GlobalRegion;
import com.realdolmen.utilities.persistence.JpaPersistenceTest;

public class LocationTest extends JpaPersistenceTest {

	@Test
	public void makingAndRetrievinglocation() throws Exception {
		Location location = new Location("redo airport", "Belgium", "BE", GlobalRegion.WESTERN_EUROPE);
		assertNull(location.getId());
		entityManager().persist(location);
		assertNotNull(location.getId());
	}
}
