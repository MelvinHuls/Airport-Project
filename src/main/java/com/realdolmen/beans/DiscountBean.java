package com.realdolmen.beans;

import java.util.List;

import javax.ejb.Stateless;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.realdolmen.Exceptions.CompanyCanOnlyHaveOneDiscountListException;
import com.realdolmen.domain.Discount;
import com.realdolmen.domain.Discounts;
import com.realdolmen.domain.Flight;

@Stateless
@RequestScoped
public class DiscountBean {
	@PersistenceContext
	EntityManager em;
	
	public Flight linkDiscounts(Flight flight) {
		if(flight.getCompany() != null) {
			flight.setDiscounts(em.createQuery("select d from Discounts d where d.company = :company", Discounts.class).setParameter("company", flight.getCompany()).getSingleResult());
			return flight;
		}
		return flight;
	}
	
	public Discounts getDiscounts(String company) throws CompanyCanOnlyHaveOneDiscountListException {
		List<Discounts> discounts = em.createQuery("select d from Discounts d where d.company = :company", Discounts.class).setParameter("company", company).getResultList();
		if(discounts.size() > 1) {
			throw new CompanyCanOnlyHaveOneDiscountListException();
		} else if (discounts.size() < 1) {
			Discounts discount = new Discounts();
			discount.setCompany(company);
			em.persist(discount);			
			return discount;
		}
		return discounts.get(0);		
	}
	
	public  List<Discount> getAllDiscounts(String company) {
		Discounts discounts = em.createQuery("select d from Discounts d where d.company = :company", Discounts.class).setParameter("company", company).getSingleResult();
		return em.createQuery("select d from Discount d where :id = d.discounts.id", Discount.class).setParameter("id", discounts.getId()).getResultList();
	}
	
	public void deleteDiscount(Discount discount) {
		em.remove(discount);
	}
	
	public void update(Discounts discounts) {
		em.merge(discounts);
	}
	
	public void add(Discount discount) {
		Discount newDiscount = new Discount(discount);
		em.persist(newDiscount);
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}
}
