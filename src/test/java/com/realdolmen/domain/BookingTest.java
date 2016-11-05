package com.realdolmen.domain;

import org.junit.Before;
import org.junit.Test;

import com.realdolmen.utilities.persistence.JpaPersistenceTest;

public class BookingTest extends JpaPersistenceTest {

	private Booking booking;

	@Before
	public void setUp() {
		booking = new Booking();
	}

	@Test
	public void makingAndRetrievingBooking() throws Exception {
		assertNull(booking.getId());
		entityManager().persist(booking);
		assertNotNull(booking.getId());
	}
}