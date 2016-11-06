package com.realdolmen.domain;

import org.junit.Before;
import org.junit.Test;

import com.realdolmen.utilities.persistence.JpaPersistenceTest;

public class PartnerTest extends JpaPersistenceTest {

	private Partner partner;

	@Before
	public void setUp() {
		partner = new Partner();
	}

	@Test
	public void makingAndRetrievingpartner() throws Exception {
		assertNull(partner.getId());
		entityManager().persist(partner);
		assertNotNull(partner.getId());
	}
}
