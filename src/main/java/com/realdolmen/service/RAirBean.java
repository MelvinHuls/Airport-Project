package com.realdolmen.service;

import java.util.List;

import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;

import com.realdolmen.domain.Partner;
import com.realdolmen.domain.User;

@SessionScoped
@Stateful
@Named("rAirBean")
public class RAirBean {
	@PersistenceContext
	private EntityManager em;

	private SessionRemote session;

	public void logOn(String username, String password) throws NonUniqueResultException {
		try {
			/*
			 * List<User>users = em .createQuery(
			 * "select u from User u where u.class = :class AND u.username = :name AND u.password = :pass"
			 * , User.class) .setParameter("name",
			 * username).setParameter("pass", password).setParameter("class",
			 * User.class).getResultList();
			 */

			Class<? extends User> kind = Partner.class;

			List<Partner> partners = em
					.createQuery("select u from " + kind.getSimpleName()
							+ " u where u.username = :name AND u.password = :pass", Partner.class)
					.setParameter("name", username).setParameter("pass", password).getResultList();
			if (partners.isEmpty()) {
				System.out.println("not a partner");
			} else {
				session = new PartnerService(partners.get(0));
				System.out.println("session made");
			}

		} catch (NonUniqueResultException ex) {
			throw new NonUniqueResultException("There is more then one user with the same username");
		} catch (NoResultException ex) {
			System.out.println("No user found with username " + username);
		}
	}

	public void testingFunctionality() {
		if (session == null) {
			System.out.println("session is empty");
		} else {
			session.testFunctionality();
		}
	}
	/*
	 * public Class<? extends User> (Class<? extends User> kind, String
	 * username, String password) { List<Class<? extends User>> partners = em
	 * .createQuery( "select u from " + kind.getSimpleName() +
	 * "u where u.username = :name AND u.password = :pass", kind)
	 * .setParameter("name", username).setParameter("pass", password)
	 * .getResultList(); }
	 */
}
