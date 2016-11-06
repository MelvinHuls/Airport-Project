package com.realdolmen.domain;

import javax.persistence.EntityManager;

import org.junit.Test;

import com.realdolmen.utilities.persistence.JpaPersistenceTest;

public class ClientTest extends JpaPersistenceTest {

	/*@Test
	public void makingAndRetrievingClient() throws Exception {
		Client client = new Client();
		assertNotNull(client);
		EntityManager em = entityManager();
		em.persist(client);
		assertNotNull(em.find(Client.class, client.getId()));
	}*/
}
