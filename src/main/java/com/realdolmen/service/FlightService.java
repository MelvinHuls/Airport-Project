package com.realdolmen.service;

import java.util.List;

import com.realdolmen.domain.Flight;
import com.realdolmen.repository.FlightRepository;

public class FlightService implements AbstractService<Flight> {
	private FlightRepository fRepo;

	@Override
	public void create(Flight flight) {
		fRepo.create(flight);
	}

	@Override
	public Flight findById(Long id) {
		return fRepo.read(id);
	}

	@Override
	public void update(Flight flight) {
		fRepo.update(flight);
	}

	@Override
	public void delete(Flight flight) {
		fRepo.delete(flight);
	}

	@Override
	public List<Flight> findAll() {
		return fRepo.findAll();
	}
}
