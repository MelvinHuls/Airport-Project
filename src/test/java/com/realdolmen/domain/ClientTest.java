package com.realdolmen.domain;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;

import com.realdolmen.utilities.persistence.JpaPersistenceTest;

public class ClientTest extends JpaPersistenceTest {	
	@Test
	public void makingAndRetrievingClient() throws Exception {
		EntityManager em = entityManager();
		Client client = new Client("Morty", "m", "Rick4eva@gmail.com");
		assertNull(client.getId());
		em.persist(client);
		assertNotNull(client.getId());
		Client c = em.find(Client.class, client.getId());
		assertEquals(c.getUsername(), client.getUsername());
	}
}
