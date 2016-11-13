package com.realdolmen.repository;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import com.realdolmen.domain.Employee;

@Stateless
public class EmployeeRepository {

	@PersistenceContext
	EntityManager em;

	public void create(Employee o) {
		em.persist(o);
	}

	public Employee read(Long id) {
		return em.find(Employee.class, id);
	}

	public String update(Employee o) {
		em.merge(o);
		return "success";
	}

	public void delete(Employee o) {
		em.remove(o);
	}

	public List<Employee> findAll() {
		try {
			return em.createNamedQuery("SELECT e FROM Employee e", Employee.class).getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}
}