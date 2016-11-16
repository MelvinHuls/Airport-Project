//package com.realdolmen.repository;
//
//import org.junit.Test;
//
//import com.realdolmen.domain.Booking;
//import com.realdolmen.domain.Client;
//import com.realdolmen.domain.Flight;
//import com.realdolmen.domain.GlobalRegion;
//import com.realdolmen.domain.Location;
//import com.realdolmen.utilities.persistence.JpaPersistenceTest;
//
//public class BookingRepositoryTest extends JpaPersistenceTest {
//
//	@Test
//	public void shouldCreateBooking() {
//		ClientRepository cRepo = new ClientRepository();
//		Client client = new Client("definatelyAUser", "thisIsMuPassword", "some@mail.com");
//		cRepo.create(client);
//
//		LocationRepository lRepo = new LocationRepository();
//		Location departure = new Location("Hethrow airport", "United Kingdom", "UK", GlobalRegion.Western_Europe);
//		Location destination = new Location("Dalas airport", "United States of America", "USA",
//				GlobalRegion.North_America);
//		lRepo.create(departure);
//		lRepo.create(destination);
//
//		FlightRepository fRepo = new FlightRepository();
//		Flight flight = new Flight("Flyan Air", 25, 250, 400, departure, destination);
//		fRepo.create(flight);
//
//		BookingRepository bRepo = new BookingRepository();
//		Booking booking = new Booking(client, flight);
//		assertNull(booking.getId());
//		bRepo.create(booking);
//		assertNotNull(booking.getId());
//	}
//}
