package com.realdolmen.repository;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.realdolmen.domain.Employee;

@Stateless
public class EmployeeRepository {
	
	@PersistenceContext
	EntityManager em;
	
	public void create(Employee o){
		em.persist(o);
	}

    public Employee read(Long id) {
        return em.find(Employee.class, id);
    }
	
	public void update(Employee o){
		em.merge(o);
	}
	
	public void delete(Employee o){
		em.remove(o);
	}
}