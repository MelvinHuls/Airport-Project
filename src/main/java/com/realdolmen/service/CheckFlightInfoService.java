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
@ManagedBean(name = "checkFlightInfoService")
public class CheckFlightInfoService {
	private Flight outwardFlight;
	private Flight returnFlight;

	public String checkDetailsFlight(Flight flight) {
		this.outwardFlight = flight;
		return "details";
	}

	public Flight getOutwardFlight() {
		return outwardFlight;
	}

	public void setOutwardFlight(Flight outwardFlight) {
		this.outwardFlight = outwardFlight;
	}

	public Flight getReturnFlight() {
		return returnFlight;
	}

	public void setReturnFlight(Flight returnFlight) {
		this.returnFlight = returnFlight;
	}

	public Boolean returnFlightSelected() {
		if (returnFlight != null) {
			return true;
		}
		return false;
	}

}
