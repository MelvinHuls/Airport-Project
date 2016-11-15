package com.realdolmen.domain;

import javax.persistence.EntityManager;

import org.junit.Test;

import com.realdolmen.utilities.persistence.JpaPersistenceTest;

public class UserTest extends JpaPersistenceTest{
	@Test
	public void makingAndRetrievingpartner() throws Exception {
		User user = new User("user123", "123456", "user.123@RAirmail.com");
		assertNull(user.getId());
		entityManager().persist(user);
		assertNotNull(user.getId());
	}
}
