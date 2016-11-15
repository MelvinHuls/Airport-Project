package com.realdolmen.beans;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.persistence.EntityManager;

import org.junit.Test;

import com.realdolmen.Exceptions.CompanyCanOnlyHaveOneDiscountListException;
import com.realdolmen.domain.Discount;
import com.realdolmen.domain.Discounts;
import com.realdolmen.domain.Flight;
import com.realdolmen.domain.Location;
import com.realdolmen.enumerations.GlobalRegion;
import com.realdolmen.utilities.persistence.JpaPersistenceTest;

public class DiscountBeanTest extends JpaPersistenceTest {
	@Test
	public void linkDiscounts() {
		EntityManager em = entityManager();
		Location departure = new Location("Brussels airport", "Belgium", "BE", GlobalRegion.WESTERN_EUROPE);
		Location destination = new Location("New York", "United States of America", "USA", GlobalRegion.NORTH_AMERICA);
		em.persist(departure);
		em.persist(destination);
		Flight flight = new Flight("testLine", 300, 200, 10, 300d, 400d, 600d, new Date(2016, 12, 01, 8, 30), new Date(2016, 12, 01, 23, 00), departure, destination);
		Discounts discounts = new Discounts();
		discounts.setCompany("testLine");
		em.persist(discounts);
		DiscountBean discountsBean = new DiscountBean();
		discountsBean.setEm(em);
		flight = discountsBean.linkDiscounts(flight);
		assertEquals("testLine", flight.getDiscounts().getCompany());
	}
	
	@Test
	public void getDiscounts() throws CompanyCanOnlyHaveOneDiscountListException {
		EntityManager em = entityManager();
		DiscountBean discountsBean = new DiscountBean();
		discountsBean.setEm(em);
		
		Discounts discounts = new Discounts();
		discounts.setCompany("TestingAir");
		
		Discount discount = new Discount(new Date(2016,10,10), new Date(2016,11,10), 0.1);
		em.persist(discount);
		discounts.addDiscount(discount);
		discount = new Discount(new Date(2016,8,10), new Date(2016,10,10), 0.05);
		em.persist(discount);
		discounts.addDiscount(discount);
		em.persist(discounts);
		
		Discounts retreivedDiscounts = discountsBean.getDiscounts("TestingAir");
		assertNotNull(retreivedDiscounts);
		assertEquals(discounts.getId(), retreivedDiscounts.getId());
		List<Discount> discountList = discountsBean.getAllDiscounts("TestingAir");
		assertNotNull(discountList);
		discount = discountList.get(0);
		assertEquals((Double)0.1, discount.getPercentage());
		discount = discountList.get(1);
		assertEquals((Double)0.05, discount.getPercentage());		
	}
}
