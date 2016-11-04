package com.realdolmen.domain;

import javax.persistence.EntityManager;

import org.junit.Test;

import com.realdolmen.utilities.persistence.JpaPersistenceTest;

public class PartnerTest extends JpaPersistenceTest {

	@Test
	public void makingAndRetrievingpartner() throws Exception {
		Partner partner = new Partner();
		assertNotNull(partner);
		EntityManager em = entityManager();
		em.persist(partner);
		assertNotNull(em.find(Partner.class, partner.getId()));
	}
}
