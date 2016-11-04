package com.realdolmen.test;

import javax.persistence.EntityManager;

import org.junit.Test;

import com.realdolmen.domain.Partner;
import com.realdolmen.domain.User;
import com.realdolmen.utilities.persistence.JpaPersistenceTest;

public class UserTest  extends JpaPersistenceTest{
	//private static final String PERSISTENCE_UNIT_NAME = "MyTestPersistenceUnit";

	@Test
	public void makingAndRetrievingUser() throws Exception {
		User user = new Partner("John Doe", "dbtest", "JD52@gmail.com", "Arilline");
		assertNotNull(user);
		//EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = entityManager();//Factory.createEntityManager();
		em.persist(user);
		assertNotNull(em.find(Partner.class, user.getId()));
	}

}