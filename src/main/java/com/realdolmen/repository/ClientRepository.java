package com.realdolmen.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import com.realdolmen.domain.Client;
import com.realdolmen.domain.Flight;

@Stateless
public class ClientRepository {

	@PersistenceContext
	EntityManager em;

	public void create(Client o) {
		em.persist(o);
	}

	public Client read(Long id) {
		return em.find(Client.class, id);
	}

	public void update(Client o) {
		em.merge(o);
	}

	public void delete(Client o) {
		em.remove(o);
	}

	public Client findByEmail(String email) {
		try {
			return em.createNamedQuery("SELECT c from Client c WHERE c.email LIKE :email", Client.class)
					.setParameter("email", email).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Client> findAll() {
		try {
			return em.createNamedQuery("SELECT c FROM Client c", Client.class).getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}
	
	
}