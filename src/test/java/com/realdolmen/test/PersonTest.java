package com.realdolmen.test;

import java.util.Date;

import javax.persistence.EntityManager;

import org.junit.Test;

import com.realdolmen.utilities.persistence.JpaPersistenceTest;


public class PersonTest extends JpaPersistenceTest{
	//private static final String PERSISTENCE_UNIT_NAME = "MyTestPersistenceUnit";

	@Test
	public void makingAndRetrievingPerson() throws Exception {
		Person person = new Person("John", "Doe", new Date(1995,02,13));
		assertNotNull(person);
		//EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
		EntityManager em = entityManager();//Factory.createEntityManager();
		em.persist(person);
		assertNotNull(em.find(Person.class, person.getId()));
	}

}
