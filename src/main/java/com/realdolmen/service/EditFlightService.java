package com.realdolmen.service;


import javax.ejb.Remote;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.realdolmen.domain.Flight;

@RequestScoped
@Remote
@Stateless
@ManagedBean(name="editFlightService")
public class EditFlightService {
	private Flight flight;

	public Flight getFlight() {
		return flight;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}
	
	public String checkDetailsFlight(Flight flight) {
		this.flight = flight;
		return "details";
	}
}
