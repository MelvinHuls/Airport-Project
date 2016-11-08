package com.realdolmen.domain;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.realdolmen.enumerations.FlightClass;
import com.realdolmen.enumerations.GlobalRegion;

public class FlightSearch {
	/*How many seats the user would like to book
	•	The traveling class
	•	An airline company that is preferred
	•	An option to enter either a departure and destination location or global region
	•	The date of departure
	•	Optionally a date of return in case the user chooses to book a return flight as well*/
	@NotNull
	private FlightClass flightclass;
	@NotNull
	private Integer seats;
	private String airlineCompany;
	private int setAirport; //desides in what way the user will search, 0 is empty, 1 is airports, 2 is general region
	private Location departure;
	private Location destination;
	private GlobalRegion region;
	@NotNull
	private Date dateDeparture;
	private Date dateReturn;
	
	public FlightSearch() {
		setAirport = 0;
	}
	
	public FlightClass getFlightclass() {
		return flightclass;
	}
	public void setFlightclass(FlightClass flightclass) {
		this.flightclass = flightclass;
	}
	public Integer getSeats() {
		return seats;
	}
	public void setSeats(Integer seats) {
		this.seats = seats;
	}
	public String getAirlineCompany() {
		return airlineCompany;
	}
	public void setAirlineCompany(String airlineCompany) {
		this.airlineCompany = airlineCompany;
	}
	public Location getDeparture() {
		return departure;
	}
	public void setDeparture(Location departure) {
		this.departure = departure;
	}
	public Location getDestination() {
		return destination;
	}
	public void setDestination(Location destination) {
		this.destination = destination;
	}
	public Date getDateDeparture() {
		return dateDeparture;
	}
	public void setDateDeparture(Date dateDeparture) {
		this.dateDeparture = dateDeparture;
	}
	public Date getDateReturn() {
		return dateReturn;
	}
	public void setDateReturn(Date dateReturn) {
		this.dateReturn = dateReturn;
	}

	public int getSetAirport() {
		return setAirport;
	}

	public void setSetAirport(int setAirport) {
		this.setAirport = setAirport;
	}
	
	public Boolean inputLocationThroughAirport() {
		if(setAirport == 1) {
			return true;
		}
		return false;
	}
	
	public Boolean inputLocationThroughGlobalRegion() {
		if(setAirport == 2) {
			return true;
		}
		return false;
	}

	public GlobalRegion getRegion() {
		return region;
	}

	public void setRegion(GlobalRegion region) {
		this.region = region;
	}
	
	public void emptyCountrySelection() {
		this.destination.setCountry(null);
		this.departure.setCountry(null);
	}	
}
