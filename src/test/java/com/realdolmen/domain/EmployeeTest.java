//package com.realdolmen.domain;
//
//import org.junit.Test;
//
//import com.realdolmen.utilities.persistence.JpaPersistenceTest;
//
//public class EmployeeTest extends JpaPersistenceTest {
//
//	@Test
//	public void makingAndRetrievingemployee() throws Exception {
//		Employee employee = new Employee("uName", "password", "foo.bar@fb.com");
//		assertNull(employee.getId());
//		entityManager().persist(employee);
//		assertNotNull(employee.getId());
//	}
//}
