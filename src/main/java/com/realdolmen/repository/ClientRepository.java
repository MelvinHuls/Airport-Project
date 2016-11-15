package com.realdolmen.repository;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.realdolmen.domain.Client;
import com.realdolmen.domain.User;

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

	public String update(Client o) {
		em.merge(o);
		return "success";
	}

	public void delete(Client o) {
		em.remove(o);
	}

	public Client findByEmail(String email) {
		TypedQuery<User> query = em.createQuery("Select u from User u where u.email = :email AND dtype like Client", User.class)
				.setParameter("email", email);
		if (query.getResultList().isEmpty()) {
			return null;
		} else {
			return (Client) query.getSingleResult();
		}
	}

	public List<Client> findAll() {
		try {
			return em.createNamedQuery("SELECT c FROM User c", Client.class).getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}

}