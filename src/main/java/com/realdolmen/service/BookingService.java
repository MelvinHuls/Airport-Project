package com.realdolmen.service;

import java.util.List;

import com.realdolmen.domain.Booking;
import com.realdolmen.repository.BookingRepository;

public class BookingService implements AbstractService<Booking> {
	private BookingRepository bRepo;

	@Override
	public void create(Booking booking) {
		bRepo.create(booking);
	}

	@Override
	public Booking findById(Long id) {
		return bRepo.read(id);
	}

	@Override
	public String update(Booking booking) {
		return bRepo.update(booking);
	}

	@Override
	public void delete(Booking booking) {
		bRepo.delete(booking);
	}

	@Override
	public List<Booking> findAll() {
		return bRepo.findAll();
	}
}
