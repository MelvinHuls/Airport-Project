package com.realdolmen.domain;

import java.util.Date;

import javax.persistence.EntityManager;

import org.junit.Test;

import com.realdolmen.Exceptions.LackingPaymentInformationException;
import com.realdolmen.Exceptions.LackingPricingInformationException;
import com.realdolmen.enumerations.FlightClass;
import com.realdolmen.enumerations.GlobalRegion;
import com.realdolmen.enumerations.PaymentStatus;
import com.realdolmen.utilities.persistence.JpaPersistenceTest;

public class BookingTest extends JpaPersistenceTest {

	private Booking booking;
	private EntityManager em;

	@Test
	public void makingAndRetrievingBooking() throws LackingPaymentInformationException, LackingPricingInformationException {
		em = entityManager();
		Client client = new Client("iAmAUser", "thisIsMyPassword", "e@mail.com");
		em.persist(client);
		Location departure = new Location("Heathrow airport", "United Kingdom", "UK", GlobalRegion.WESTERN_EUROPE);
		Location destination = new Location("Dallas airport", "United States of America", "USA",
				GlobalRegion.NORTH_AMERICA);
		em.persist(departure);
		em.persist(destination);
		Flight flight = new Flight("Flyan Air", 25, 200, 400, 250d, 300d, 500d, new Date(2016,11,30,10,0,0), new Date(2016,11,30,23,30,0), departure, destination);
		em.persist(flight);
		booking = new Booking(client, flight, PaymentStatus.PAYMENT_PENDING, FlightClass.ECONOMY, 5);
		booking.calculateAndSetTotalPrice();
		assertNull(booking.getId());
		em.persist(booking);
		assertNotNull(booking.getId());
	}
	
	@Test
	public void calculateAndSetTotalPrice() throws LackingPaymentInformationException, LackingPricingInformationException {
		em = entityManager();
		Client client = new Client("iAmAUser", "thisIsMyPassword", "e@mail.com");
		em.persist(client);
		Location departure = new Location("Heathrow airport", "United Kingdom", "UK", GlobalRegion.WESTERN_EUROPE);
		Location destination = new Location("Dallas airport", "United States of America", "USA",
				GlobalRegion.NORTH_AMERICA);
		em.persist(departure);
		em.persist(destination);
		Flight flight = new Flight("Flyan Air", 25, 200, 400, 250d, 300d, 500d, new Date(2016,11,30,10,0,0), new Date(2016,11,30,23,30,0), departure, destination);
		em.persist(flight);
		booking = new Booking(client, flight, PaymentStatus.PAYMENT_PENDING, FlightClass.ECONOMY, 5);
		booking.calculateAndSetTotalPrice();
		assertEquals((Double)(5d*(250d*1.1d)), booking.getTotalPrice());
	}
	
	@Test
	public void returnFlightBooked() {
		em = entityManager();	
		Client client = new Client("iAmAUser", "thisIsMyPassword", "e@mail.com");
		em.persist(client);
		Location departure = new Location("Heathrow airport", "United Kingdom", "UK", GlobalRegion.WESTERN_EUROPE);
		Location destination = new Location("Dallas airport", "United States of America", "USA",
				GlobalRegion.NORTH_AMERICA);
		em.persist(departure);
		em.persist(destination);
		Flight flight = new Flight("Flyan Air", 25, 200, 400, 250d, 300d, 500d, new Date(2016,11,30,10,0,0), new Date(2016,11,30,23,30,0), departure, destination);
		em.persist(flight);
		booking = new Booking(client, flight, PaymentStatus.PAYMENT_PENDING, FlightClass.ECONOMY, 5);
		assertTrue(!booking.returnFlightBooked());
	}
}