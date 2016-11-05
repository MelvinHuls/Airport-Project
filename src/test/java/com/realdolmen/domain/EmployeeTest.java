package com.realdolmen.domain;

import org.junit.Before;
import org.junit.Test;

import com.realdolmen.utilities.persistence.JpaPersistenceTest;

public class EmployeeTest extends JpaPersistenceTest {

	private Employee employee;

	@Before
	public void setUp() {
		employee = new Employee();
	}

	@Test
	public void makingAndRetrievingemployee() throws Exception {
		assertNull(employee.getId());
		entityManager().persist(employee);
		assertNotNull(employee.getId());
	}
}
