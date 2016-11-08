package com.realdolmen.domain;

import javax.persistence.EntityManager;

import org.junit.Test;

import com.realdolmen.enumerations.GlobalRegion;
import com.realdolmen.utilities.persistence.JpaPersistenceTest;

public class BookingTest extends JpaPersistenceTest {

	private Booking booking;
	private EntityManager em;

	@Test
	public void makingAndRetrievingBooking() throws Exception {
		em = entityManager();
		Client client = new Client("iAmAUser", "thisIsMyPassword", "e@mail.com");
		em.persist(client);
		Location departure = new Location("Heathrow airport", "United Kingdom", "UK", GlobalRegion.Western_Europe);
		Location destination = new Location("Dallas airport", "United States of America", "USA",
				GlobalRegion.North_America);
		em.persist(departure);
		em.persist(destination);
		Flight flight = new Flight("Flyan Air", 25, 200, 400, departure, destination);
		em.persist(flight);
		booking = new Booking(client, flight);
		assertNull(booking.getId());
		em.persist(booking);
		assertNotNull(booking.getId());
	}
}