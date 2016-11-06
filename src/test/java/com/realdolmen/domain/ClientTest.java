package com.realdolmen.domain;

import org.junit.Before;
import org.junit.Test;

import com.realdolmen.utilities.persistence.JpaPersistenceTest;

public class ClientTest extends JpaPersistenceTest {

	private Client client;

	@Before
	public void setUp() {
		client = new Client();
	}

	@Test
	public void makingAndRetrievingClient() throws Exception {
		assertNull(client.getId());
		entityManager().persist(client);
		assertNotNull(client.getId());
	}
}
