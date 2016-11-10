package com.realdolmen.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;

import org.junit.Test;

import com.realdolmen.utilities.persistence.JpaPersistenceTest;

public class DiscountsTest extends JpaPersistenceTest {
	@Test
	public void makingAndRetrievingDiscounts() throws Exception {
		EntityManager em = entityManager();
		

		Discounts discounts = new Discounts();
		Discount discount = new Discount(discounts, new Date(2017, 10, 12), new Date(2017, 11, 12), 0.05);
		em.persist(discount);
		discounts.addDiscount(discount);

		em.persist(discounts);
		assertNotNull(em.find(discounts.getClass(), discounts.getId()));
	}
	
	@Test
	public void makingAndRetrievingSeatDiscounts() throws Exception {
		EntityManager em = entityManager();		

		Discounts discounts = new Discounts();
		discounts.addSeatsDiscount(2, 0.05);
		discounts.addSeatsDiscount(5,0.1);

		em.persist(discounts);
		assertNotNull(em.find(discounts.getClass(), discounts.getId()));
	}

}
