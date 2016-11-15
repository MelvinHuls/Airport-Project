package com.realdolmen.repository;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.realdolmen.domain.User;

public class UserRepository {

	@PersistenceContext
	EntityManager em;

	public String create(User o) {
		em.persist(o);
		return "success";
	}

	public User read(Long id) {
		return em.find(User.class, id);
	}

	public String update(User o) {
		em.merge(o);
		return "success";
	}

	public void delete(User o) {
		em.remove(o);
	}

	public List<User> findAll() {
		try {
			return em.createNamedQuery("SELECT e FROM User e", User.class).getResultList();
		} catch (NoResultException e) {
			return Collections.emptyList();
		}
	}

	public User findByEmail(String email) {
		TypedQuery<User> query = em.createQuery("Select u from User u where u.email = :email", User.class).setParameter("email", email);
		if (query.getResultList().isEmpty()){
			return null;
		} else {
			return query.getSingleResult();
		}
	}
	/*public User findUserByEmail(String email) {

		TypedQuery<User> q = em.createQuery("select u from User u where u.email = :email", User.class);
		q.setParameter("email", email);
		if (q.getResultList().isEmpty()) {
			return null;
		}
		return q.getSingleResult();
	}*/
	
	

}
