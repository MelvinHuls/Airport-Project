package com.realdolmen.repository;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.realdolmen.domain.Location;

@Stateless
public class LocationRepository {
	
	@PersistenceContext
	EntityManager em;
	
	public void create(Location o){
		em.persist(o);
	}

    public Location read(Long id) {
        return em.find(Location.class, id);
    }
	
	public void update(Location o){
		em.merge(o);
	}
	
	public void delete(Location o){
		em.remove(o);
	}
}