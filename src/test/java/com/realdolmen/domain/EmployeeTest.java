package com.realdolmen.domain;

import javax.persistence.EntityManager;

import org.junit.Test;

import com.realdolmen.utilities.persistence.JpaPersistenceTest;

public class EmployeeTest extends JpaPersistenceTest {

	/*@Test
	public void makingAndRetrievingemployee() throws Exception {
		Employee employee = new Employee();
		assertNotNull(employee);
		EntityManager em = entityManager();
		em.persist(employee);
		assertNotNull(em.find(Employee.class, employee.getId()));
	}*/
}
