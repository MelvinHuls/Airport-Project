package com.realdolmen.domain;

import javax.persistence.EntityManager;

import org.junit.Test;

import com.realdolmen.utilities.persistence.JpaPersistenceTest;

public class EmployeeTest extends JpaPersistenceTest {

	@Test
	public void makingAndRetrievingemployee() throws Exception {
		EntityManager em = entityManager();
		Employee employee = new Employee("uName", "password", "foo.bar@fb.com");
		assertNull(employee.getId());
		em.persist(employee);
		assertNotNull(employee.getId());
		Employee e = em.find(Employee.class, employee.getId());
		assertEquals(e.getUsername(), employee.getUsername());
	}
}
