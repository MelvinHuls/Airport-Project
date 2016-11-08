package com.realdolmen.service;

import com.realdolmen.domain.Booking;
import com.realdolmen.repository.BookingRepository;

public class BookingService {
	private BookingRepository bRepo;

	public void create(Booking booking) {
		bRepo.create(booking);
	}

	public Booking read(Long id) {
		return bRepo.read(id);
	}

	public void update(Booking booking) {
		bRepo.update(booking);
	}

	public void delete(Booking booking) {
		bRepo.delete(booking);
	}
}
