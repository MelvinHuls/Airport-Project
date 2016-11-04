package com.realdolmen.domain;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.BeforeClass;
import org.junit.Test;

import com.realdolmen.utilities.persistence.JpaPersistenceTest;

public class EmployeeTest extends JpaPersistenceTest {
	private EntityManagerFactory entityManagerFactory;
	@BeforeClass
	public void setUp(){
		entityManagerFactory = Persistence.createEntityManagerFactory("theFactory");		
	}
	@Test
	public void makingAndRetrievingemployee() throws Exception {
		Employee employee = new Employee();
		assertNotNull(employee);
		EntityManager em = entityManagerFactory.createEntityManager();
		em.persist(employee);
		assertNotNull(em.find(Employee.class, employee.getId()));
	}
}
