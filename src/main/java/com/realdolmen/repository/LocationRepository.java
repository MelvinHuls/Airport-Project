package com.realdolmen.repository;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import com.realdolmen.domain.Location;

@Stateless
public class LocationRepository {

	@PersistenceContext
	EntityManager em;

	public void create(Location o) {
		em.persist(o);
	}

	public Location read(Long id) {
		return em.find(Location.class, id);
	}

	public void update(Location o) {
		em.merge(o);
	}

	public void delete(Location o) {
		em.remove(o);
	}

	public List<Location> findAll() {
		try {
			return em.createNamedQuery("SELECT l FROM Location l", Location.class).getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}

	public List<String> getAirportsCountry(String country) {
		return em.createQuery("select distinct c.airport from Location c where c.country = :country", String.class)
				.setParameter("country", country).getResultList();
	}

	public List<String> getCountries() {
		return em.createQuery("select distinct l.country from Location l", String.class).getResultList();
	}
}