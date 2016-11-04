package com.realdolmen.domain;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.BeforeClass;
import org.junit.Test;

import com.realdolmen.utilities.persistence.JpaPersistenceTest;

public class PartnerTest extends JpaPersistenceTest {
	private EntityManagerFactory entityManagerFactory;
	@BeforeClass
	public void setUp(){
		entityManagerFactory = Persistence.createEntityManagerFactory("theFactory");		
	}
	
	@Test
	public void makingAndRetrievingpartner() throws Exception {
		Partner partner = new Partner();
		assertNotNull(partner);
		EntityManager em = entityManagerFactory.createEntityManager();
		em.persist(partner);
		assertNotNull(em.find(Partner.class, partner.getId()));
	}
}
