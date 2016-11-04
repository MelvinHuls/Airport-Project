package com.realdolmen.domain;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.BeforeClass;
import org.junit.Test;

import com.realdolmen.utilities.persistence.JpaPersistenceTest;

public class ClientTest extends JpaPersistenceTest {
	private EntityManagerFactory entityManagerFactory;
	@BeforeClass
	public void setUp(){
		entityManagerFactory = Persistence.createEntityManagerFactory("theFactory");		
	}
	@Test
	public void makingAndRetrievingClient() throws Exception {
		Client client = new Client();
		assertNotNull(client);
		EntityManager em = entityManagerFactory.createEntityManager();
		em.persist(client);
		assertNotNull(em.find(Client.class, client.getId()));
	}
}
