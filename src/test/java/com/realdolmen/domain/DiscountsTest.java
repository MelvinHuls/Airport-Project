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
		discounts.setCompany("Cavendish");
		Discount discount = new Discount(discounts, new Date(2017, 10, 12), new Date(2017, 11, 12), 0.05);
		em.persist(discount);
		discounts.addDiscount(discount);

		em.persist(discounts);
		Discounts retreivedDiscounts = em.find(discounts.getClass(), discounts.getId());
		assertNotNull(retreivedDiscounts);
		assertEquals(discounts.getId(), retreivedDiscounts.getId());
		assertNotNull(retreivedDiscounts.getDiscounts());
		Discount retreivedDiscount = retreivedDiscounts.getDiscounts().get(0);
		assertNotNull(retreivedDiscount);
		assertEquals(discount.getId(), retreivedDiscount.getId());
	}
	
	@Test
	public void makingAndRetrievingSeatDiscounts() throws Exception {
		Discounts discounts = new Discounts();
		discounts.addSeatsDiscount(2, 0.05);
		discounts.addSeatsDiscount(5, 0.1);
		
		assertEquals((Double)0.05, discounts.getSeatsDiscount().get(2));		
		assertEquals((Double)0.1, discounts.getSeatsDiscount().get(5));	
	}
	
	@Test
	public void calculateSeatPrice() {
		Discounts discounts = new Discounts();
		discounts.setWeekendDiscount(0.05);
		discounts.setNightlyDiscount(0.025);
		assertEquals((Double)(500d*0.975d), discounts.calculateSeatPrice(500d, new Date(2016,11,14,1,18)));
		assertEquals((Double)(500d*0.95d), discounts.calculateSeatPrice(500d, new Date(2016,11,19,9,18)));
		assertEquals((Double)(500d*0.95d*0.975), discounts.calculateSeatPrice(500d, new Date(2016,11,19,1,18)));
		
		discounts.addDiscount(new Discount(new Date(1991,05,01), new Date(1991,10,05), 0.1));
		assertEquals((Double)500d, discounts.calculateSeatPrice(500d, new Date(2016,11,15,9,18)));

		discounts.addDiscount(new Discount(new Date(2016,10,01), new Date(2016,11,30), 0.1));
		assertEquals((Double)(500d*0.9), discounts.calculateSeatPrice(500d, new Date(2016,11,15,9,18)));
		assertEquals((Double)(500d*0.95*0.9), discounts.calculateSeatPrice(500d, new Date(2016,11,19,9,18)));
		assertEquals((Double)(500d*0.95*0.9*0.975), discounts.calculateSeatPrice(500d, new Date(2016,11,19,1,18)));
	}
	
	@Test
	public void calculateDiscount() {
		Discounts discounts = new Discounts();
		discounts.setWeekendDiscount(0.05);
		discounts.setNightlyDiscount(0.025);
		assertEquals((Double)((500-(500*0.975))*5), discounts.calculateDiscount(500d, new Date(2016,11,14,1,18), 5));
		assertEquals((Double)((500-(500*0.95d))*5), discounts.calculateDiscount(500d, new Date(2016,11,19,9,18), 5));
		assertEquals((Double)((500-(500d*0.95d*0.975))*5), discounts.calculateDiscount(500d, new Date(2016,11,19,1,18), 5));
		
		discounts.addDiscount(new Discount(new Date(1991,05,01), new Date(1991,10,05), 0.1));
		assertEquals((Double)(0d), discounts.calculateDiscount(500d, new Date(2016,11,15,9,18), 5));

		discounts.addDiscount(new Discount(new Date(2016,10,01), new Date(2016,11,30), 0.1));
		assertEquals((Double)((500-(500d*0.9))*5), discounts.calculateDiscount(500d, new Date(2016,11,15,9,18), 5));
		assertEquals((Double)((500-(500d*0.95*0.9))*5), discounts.calculateDiscount(500d, new Date(2016,11,19,9,18), 5));
		assertEquals((Double)((500-(500d*0.95*0.9*0.975))*5), discounts.calculateDiscount(500d, new Date(2016,11,19,1,18), 5));		
	}
	
	@Test
	public void calculatePrice() {
		Discounts discounts = new Discounts();
		discounts.setWeekendDiscount(0.05);
		discounts.setNightlyDiscount(0.025);
		assertEquals((Double)((500*0.975)*5), discounts.calculatePrice(500d, new Date(2016,11,14,1,18), 5));
		assertEquals((Double)((500*0.95d)*5), discounts.calculatePrice(500d, new Date(2016,11,19,9,18), 5));
		assertEquals((Double)((500d*0.95d*0.975)*5), discounts.calculatePrice(500d, new Date(2016,11,19,1,18), 5));
		
		discounts.addDiscount(new Discount(new Date(1991,05,01), new Date(1991,10,05), 0.1));
		assertEquals((Double)(0d), discounts.calculateDiscount(500d, new Date(2016,11,15,9,18), 5));

		discounts.addDiscount(new Discount(new Date(2016,10,01), new Date(2016,11,30), 0.1));
		assertEquals((Double)((500d*0.9)*5), discounts.calculatePrice(500d, new Date(2016,11,15,9,18), 5));
		assertEquals((Double)((500d*0.95*0.9)*5), discounts.calculatePrice(500d, new Date(2016,11,19,9,18), 5));
		assertEquals((Double)((500d*0.95*0.9*0.975)*5), discounts.calculatePrice(500d, new Date(2016,11,19,1,18), 5));		
	}
	
	

}
