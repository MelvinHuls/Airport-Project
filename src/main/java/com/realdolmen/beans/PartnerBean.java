package com.realdolmen.beans;

import javax.ejb.Stateless;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.realdolmen.domain.Partner;

@Stateless
@RequestScoped
public class PartnerBean {
	@PersistenceContext
	private EntityManager em;
	
	public Partner getPartner(Long id) {
		return em.find(Partner.class, id);
	}
	
	public void updatePartner(Partner partner) {
		em.merge(partner);
	}
	
	public void createPartner(Partner partner) {
		em.persist(partner);
	}	
}
