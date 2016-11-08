package com.realdolmen.repository;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.realdolmen.domain.Partner;

@Stateless
public class PartnerRepository {

	@PersistenceContext
	EntityManager em;

	public void create(Partner o) {
		em.persist(o);
	}

	public Partner read(Long id) {
		return em.find(Partner.class, id);
	}

	public void update(Partner o) {
		em.merge(o);
	}

	public void delete(Partner o) {
		em.remove(o);
	}
}