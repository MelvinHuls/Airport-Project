package com.realdolmen.domain;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;

import com.realdolmen.Exceptions.LackingPricingInformationException;
import com.realdolmen.enumerations.GlobalRegion;
import com.realdolmen.utilities.persistence.JpaPersistenceTest;

public class FlightTest extends JpaPersistenceTest {
	// private EntityManager em = entityManager();

	@Test
	public void makingAndRetrievingFlight() throws Exception {
		Location departure = new Location("Brussels airport", "Belgium", "BE", GlobalRegion.WESTERN_EUROPE);
		Location destination = new Location("New York", "United States of America", "USA", GlobalRegion.NORTH_AMERICA);
		entityManager().persist(departure);
		entityManager().persist(destination);
		Flight flight = new Flight("airliner", 300, 200, 10, 300d, 400d, 600d, new Date(2016,12,01,8,30), new Date(2016,12,01,23,00), departure, destination);
		assertNotNull(flight);
		entityManager().persist(flight);
		assertNotNull(entityManager().find(Flight.class, flight.getId()));
	}
	
	@Test
	public void calculateArrivalTime() {
		Location departure = new Location("Brussels airport", "Belgium", "BE", GlobalRegion.WESTERN_EUROPE);
		Location destination = new Location("New York", "United States of America", "USA", GlobalRegion.NORTH_AMERICA);
		Flight flight = new Flight("airliner", 300, 200, 10, 300d, 400d, 600d, new Date(2016,12,01,8,30), new Date(2016,12,01,23,00), departure, destination);
		assertEquals(new Date(2016,12,02,7,30), flight.getArrivalTime());
	}
	
	@Test
	public void calculatePrice() {
		Location departure = new Location("Brussels airport", "Belgium", "BE", GlobalRegion.WESTERN_EUROPE);
		Location destination = new Location("New York", "United States of America", "USA", GlobalRegion.NORTH_AMERICA);
		Flight flight = new Flight("airliner", 300, 200, 10, 300d, 400d, 600d, new Date(2016,12,03,1,30), new Date(2016,12,01,23,00), departure, destination);
		assertEquals((Double) 330d,flight.calculatePriceOneSeatEconomy());
		Double price = (double) Math.round(flight.calculatePriceOneSeatBusiness() * 100);
		assertEquals((Double) 440d, (Double)(price/100));
		assertEquals((Double) 660d,flight.calculatePriceOneSeatFirstClass());
		flight.setCustomMarginPriceEconomy(250d);
		assertEquals((Double) 250d,flight.calculatePriceOneSeatEconomy());		
		Discounts discounts = new Discounts();
		discounts.setCompany(flight.getCompany());
		discounts.addDiscount(new Discount(new Date(2016,10,10), new Date(2017,01,01),0.1));
		discounts.setNightlyDiscount(0.05);
		discounts.setWeekendDiscount(0.025);
		flight.setDiscounts(discounts);	
		assertEquals((Double) (250d*0.95*0.975*0.9),flight.calculatePriceOneSeatEconomy());	
	}
	
	@Test
	public void calculateTotalPrice() throws LackingPricingInformationException {
		Location departure = new Location("Brussels airport", "Belgium", "BE", GlobalRegion.WESTERN_EUROPE);
		Location destination = new Location("New York", "United States of America", "USA", GlobalRegion.NORTH_AMERICA);
		Flight flight = new Flight("airliner", 300, 200, 10, 300d, 400d, 600d, new Date(2016,12,03,1,30), new Date(2016,12,01,23,00), departure, destination);
		assertEquals((Double) (330d*2),flight.calculateTotalPriceEconomy(2));
		Double price = (double) Math.round(flight.calculateTotalPriceBusiness(2) * 100);
		assertEquals((Double) (440d*2), (Double)(price/100));
		assertEquals((Double) (660d*2),flight.calculateTotalPriceFirstClass(2));
		flight.setCustomMarginPriceEconomy(250d);
		assertEquals((Double) (250d*2),flight.calculateTotalPriceEconomy(2));		
		Discounts discounts = new Discounts();
		discounts.setCompany(flight.getCompany());
		discounts.addDiscount(new Discount(new Date(2016,10,10), new Date(2017,01,01),0.1));
		discounts.setNightlyDiscount(0.05);
		discounts.setWeekendDiscount(0.025);
		flight.setDiscounts(discounts);	
		assertEquals((Double) ((250d*0.95*0.975*0.9)*2),flight.calculateTotalPriceEconomy(2));	
		discounts.addSeatsDiscount(2, 0.05);
		flight.setDiscounts(discounts);
		assertEquals((Double) ((250d*0.95*0.975*0.9*0.95)*5),flight.calculateTotalPriceEconomy(5));	
	}
	
	@Test
	public void calculateDiscount() {
		Location departure = new Location("Brussels airport", "Belgium", "BE", GlobalRegion.WESTERN_EUROPE);
		Location destination = new Location("New York", "United States of America", "USA", GlobalRegion.NORTH_AMERICA);
		Flight flight = new Flight("airliner", 300, 200, 10, 300d, 400d, 600d, new Date(2016,12,03,1,30), new Date(2016,12,01,23,00), departure, destination);
		assertEquals((Double) 0d,flight.calculateDiscountEconomy(2));
		Double price = (double) Math.round(flight.calculateDiscountBusiness(2) * 100);
		assertEquals((Double) 0d, (Double)(price/100));
		assertEquals((Double) 0d,flight.calculateDiscountFirstClass(2));
		flight.setCustomMarginPriceEconomy(250d);
		assertEquals((Double) 0d,flight.calculateDiscountEconomy(2));		
		Discounts discounts = new Discounts();
		discounts.setCompany(flight.getCompany());
		discounts.addDiscount(new Discount(new Date(2016,10,10), new Date(2017,01,01),0.1));
		discounts.setNightlyDiscount(0.05);
		discounts.setWeekendDiscount(0.025);
		flight.setDiscounts(discounts);	
		assertEquals((Double) ((250-(250d*0.95*0.975*0.9))*2),flight.calculateDiscountEconomy(2));	
		discounts.addSeatsDiscount(2, 0.05);
		flight.setDiscounts(discounts);
		assertEquals((Double) ((250-(250d*0.95*0.975*0.9*0.95))*5),flight.calculateDiscountEconomy(5));	
	}
}
