package com.realdolmen.repository;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.realdolmen.domain.Employee;
import com.realdolmen.domain.User;

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
			return em.createNamedQuery("SELECT e FROM User e", Employee.class).getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}

	public Employee findByEmail(String email) {
		TypedQuery<User> query = em.createQuery("Select u from User u where u.email = :email AND dtype like Employee", User.class)
				.setParameter("email", email);
		if (query.getResultList().isEmpty()) {
			return null;
		} else {
			return (Employee) query.getSingleResult();
		}
	}
}