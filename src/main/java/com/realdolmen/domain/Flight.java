package com.realdolmen.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

@Entity
public class Flight {

	@Id
	@GeneratedValue
	private Long id;
	private Integer seatsEconomy;
	private Integer seatsBusiness;
	private Integer seatsFirstClass;
	private BigDecimal priceEconomy;
	private BigDecimal priceBusiness;
	private BigDecimal priceFirstClass;

	@ManyToOne
	@NotNull
	private Location departure;

	@ManyToOne
	@NotNull
	private Location destination;
	
	@NotNull
	private Date departureTime;
	@NotNull
	@Future
	private Date arrivalTime;	

	@NotNull
	private String company;

	public Flight() {
	}

	public Flight(String company, Integer seatsEconomy, Integer seatsBusiness, Integer seatsFirstClass, Date departureTime, Date arrivalTime,
			Location departure, Location destination) {
		this.seatsEconomy = seatsEconomy;
		this.seatsBusiness = seatsBusiness;
		this.seatsFirstClass = seatsFirstClass;
		this.departure = departure;
		this.destination = destination;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
		this.company = company;
	}

	public Flight(String company, Integer seatsEconomy, Integer seatsBusiness, Integer seatsFirstClass,
			BigDecimal priceEconomy, BigDecimal priceBusiness, BigDecimal priceFirstClass,
			/*TravellingClass travellingClass,*/ Location departure, Location destination) {
		this.company = company;
		this.seatsEconomy = seatsEconomy;
		this.seatsBusiness = seatsBusiness;
		this.seatsFirstClass = seatsFirstClass;
		this.priceEconomy = priceEconomy;
		this.priceBusiness = priceBusiness;
		this.priceFirstClass = priceFirstClass;
		this.departure = departure;
		this.destination = destination;
	}

	public Flight(String company, Integer seatsEconomy, Integer seatsBusiness, Integer seatsFirstClass,
			BigDecimal priceEconomy, BigDecimal priceBusiness, BigDecimal priceFirstClass, Date departureTime, Date arrivalTime, Location departure, Location destination) {
		this.company = company;
		this.seatsEconomy = seatsEconomy;
		this.seatsBusiness = seatsBusiness;
		this.seatsFirstClass = seatsFirstClass;
		this.priceEconomy = priceEconomy;
		this.priceBusiness = priceBusiness;
		this.priceFirstClass = priceFirstClass;
		this.departure = departure;
		this.destination = destination;
		this.departureTime = departureTime;
		this.arrivalTime = arrivalTime;
	}

	public Integer getSeatsEconomy() {
		return seatsEconomy;
	}

	public void setSeatsEconomy(Integer seatsEconomy) {
		this.seatsEconomy = seatsEconomy;
	}

	public Integer getSeatsBusiness() {
		return seatsBusiness;
	}

	public void setSeatsBusiness(Integer seatsBusiness) {
		this.seatsBusiness = seatsBusiness;
	}

	public Integer getSeatsFirstClass() {
		return seatsFirstClass;
	}

	public void setSeatsFirstClass(Integer seatsFirstClass) {
		this.seatsFirstClass = seatsFirstClass;
	}

	public BigDecimal getPriceEconomy() {
		return priceEconomy;
	}

	public void setPriceEconomy(BigDecimal priceEconomy) {
		this.priceEconomy = priceEconomy;
	}

	public BigDecimal getPriceBusiness() {
		return priceBusiness;
	}

	public void setPriceBusiness(BigDecimal priceBusiness) {
		this.priceBusiness = priceBusiness;
	}

	public BigDecimal getPriceFirstClass() {
		return priceFirstClass;
	}

	public void setPriceFirstClass(BigDecimal priceFirstClass) {
		this.priceFirstClass = priceFirstClass;
	}

	public Long getFlightDuration() {
		return arrivalTime.getTime() - departureTime.getTime();
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

	public Long getId() {
		return id;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public Integer getNumberOfSeats() {
		return seatsBusiness + seatsEconomy + seatsFirstClass;
	}
	
	public Date getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public Date getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}
	
	

}
