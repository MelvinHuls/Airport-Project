package com.realdolmen.repository;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import com.realdolmen.domain.Partner;

@Stateless
public class PartnerRepository {

	@PersistenceContext
	EntityManager em;

	/*
	 * List<Flight> flights = em.createQuery(
	 * "select f from Flight f where f.company = :company", Flight.class)
	 * .setParameter("company", partner.getCompany()).getResultList();
	 */

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

	public List<Partner> findAll() {
		try {
			return em.createNamedQuery("SELECT p FROM Partner p", Partner.class).getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}
}