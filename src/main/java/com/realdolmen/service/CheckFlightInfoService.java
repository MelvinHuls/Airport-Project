package com.realdolmen.service;

import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

import com.realdolmen.Exceptions.LackingPricingInformationException;
import com.realdolmen.Exceptions.NotEnoughSeatsException;
import com.realdolmen.beans.BookFlightBean;
import com.realdolmen.domain.Flight;
import com.realdolmen.enumerations.FlightClass;

//@RequestScoped
@SessionScoped
@Remote
@Stateless
@ManagedBean(name = "checkFlightInfoService")
public class CheckFlightInfoService {
	private Flight outwardFlight;
	private Flight returnFlight;

	@EJB
	BookFlightBean bookFlight;

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

	public Boolean returnFlightNotSelected() {
		if (returnFlight == null) {
			return true;
		}
		return false;
	}

	public BookFlightBean getBookFlight() {
		return bookFlight;
	}

	public void setBookFlight(BookFlightBean bookFlight) {
		this.bookFlight = bookFlight;
	}

	public String BookFlightCreditCard(FlightClass flightclass, Integer seats, String creditcardNumber,
			Date expirationDate) throws NotEnoughSeatsException, LackingPricingInformationException {
		try {
			if (bookFlight == null) {
				bookFlight = new BookFlightBean();
			}
			if (returnFlight == null) {				
				return this.bookFlight.bookFlightCreditCard(outwardFlight, creditcardNumber, expirationDate,
						flightclass, seats);
			} else {
				return this.bookFlight.bookFlightCreditCard(outwardFlight, returnFlight,
						creditcardNumber, expirationDate, flightclass, seats);
			}
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
			return "failure";
		}
	}

	public String BookFlightEndorsement(FlightClass flightclass, Integer seats)
			throws NotEnoughSeatsException, LackingPricingInformationException {
		if (bookFlight == null) {
			bookFlight = new BookFlightBean();
		}
		if (returnFlight == null) {
			return this.bookFlight.bookFlightEndorsement(outwardFlight, flightclass, seats);
		} else {
			return this.bookFlight.bookFlightEndorsement(outwardFlight, returnFlight, flightclass,
					seats);
		}
	}	
}
