package com.realdolmen.beans;

import javax.ejb.Stateless;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.realdolmen.domain.Flight;

@Stateless
@RequestScoped
public class FlightBean {
	 @PersistenceContext 
	 EntityManager em;
	 
	public Flight getFlight(Long id) {
		return em.createQuery("select f from Flight f where f.id = :id", Flight.class).setParameter("id", id).getSingleResult();
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}
	
	
}
