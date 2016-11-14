package com.realdolmen.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;

import com.realdolmen.domain.Flight;
import com.realdolmen.domain.Partner;
import com.realdolmen.repository.FlightRepository;

@Stateless
@ManagedBean(name="flightService")
public class FlightService implements AbstractService<Flight> {
	@EJB
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
	public String update(Flight flight) {
		return fRepo.update(flight);
	}

	@Override
	public void delete(Flight flight) {
		fRepo.delete(flight);
	}

	@Override
	public List<Flight> findAll() {
		return fRepo.findAllFlights();
	}
	
	public List<Flight> getFlightsByCompany(Partner partner) {
		return fRepo.getFlightsByCompany(partner);
	}
}
