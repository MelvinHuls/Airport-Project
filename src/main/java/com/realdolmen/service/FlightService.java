package com.realdolmen.service;

import com.realdolmen.domain.Flight;
import com.realdolmen.repository.FlightRepository;

public class FlightService {
	private FlightRepository fRepo;

	public void create(Flight flight) {
		fRepo.create(flight);
	}

	public Flight read(Long id) {
		return fRepo.read(id);
	}

	public void update(Flight flight) {
		fRepo.update(flight);
	}

	public void delete(Flight flight) {
		fRepo.delete(flight);
	}
}
