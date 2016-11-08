package com.realdolmen.domain;

import org.junit.Test;

import com.realdolmen.utilities.persistence.JpaPersistenceTest;

public class PartnerTest extends JpaPersistenceTest {

	@Test
	public void makingAndRetrievingpartner() throws Exception {
		Partner partner = new Partner("partner123", "123456", "partner.123@FlyanMail.com", "Flyan Air");
		assertNull(partner.getId());
		entityManager().persist(partner);
		assertNotNull(partner.getId());
	}
}
