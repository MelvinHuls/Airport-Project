package com.realdolmen.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Flight {
	@Id
	@GeneratedValue
	private Long id;
	private Integer seatsEconomy;
	private Integer seatsBusiness;
	private Integer seatsFirstClass;

	private Double priceEconomy;
	private Double priceBusiness;
	private Double priceFirstClass;
	private Double customMarginPriceEconomy;
	private Double customMarginPriceBusiness;
	private Double customMarginPriceFirstClass;

	@ManyToOne
	private Discounts discounts;

	@ManyToOne
	@NotNull
	private Location departure;

	@ManyToOne
	@NotNull
	private Location destination;

	@NotNull
	private Date departureTime;

	@NotNull
	private Date duration;

	@NotNull
	private String company;

	public Flight() {
	}

	public Flight(String company, Integer seatsEconomy, Integer seatsBusiness, Integer seatsFirstClass,
			Date departureTime, Date duration, Location departure, Location destination) {
		this.seatsEconomy = seatsEconomy;
		this.seatsBusiness = seatsBusiness;
		this.seatsFirstClass = seatsFirstClass;
		this.departure = departure;
		this.destination = destination;
		this.departureTime = departureTime;
		this.duration = duration;
		this.company = company;
	}

	public Flight(String company, Integer seatsEconomy, Integer seatsBusiness, Integer seatsFirstClass,
			Double priceEconomy, Double priceBusiness, Double priceFirstClass,
			/* TravellingClass travellingClass, */ Location departure, Location destination) {
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
			Double priceEconomy, Double priceBusiness, Double priceFirstClass, Date departureTime, Date duration,
			Location departure, Location destination) {
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
		this.duration = duration;
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

	public Double getPriceEconomy() {
		return priceEconomy;
	}

	public void setPriceEconomy(Double priceEconomy) {
		this.priceEconomy = priceEconomy;
	}

	public Double getPriceBusiness() {
		return priceBusiness;
	}

	public void setPriceBusiness(Double priceBusiness) {
		this.priceBusiness = priceBusiness;
	}

	public Double getPriceFirstClass() {
		return priceFirstClass;
	}

	public void setPriceFirstClass(Double priceFirstClass) {
		this.priceFirstClass = priceFirstClass;
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
		return new Date(departureTime.getTime()
				+ ((((duration.getHours() * 60) + duration.getMinutes()) * 60) + duration.getSeconds()) * 1000);
	}

	public Date getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(Date departureTime) {
		this.departureTime = departureTime;
	}

	public Date getDuration() {
		return duration;
	}

	public void setDuration(Date duration) {
		this.duration = duration;
	}

	public Discounts getDiscounts() {
		return discounts;
	}

	public void setDiscounts(Discounts discounts) {
		this.discounts = discounts;
	}

	public Double calculatePriceEconomy() {
		if (discounts == null) {
			return this.priceEconomy * 1.1; 
		}
		if (this.customMarginPriceEconomy != null) {
			return discounts.calculatePrice(this.customMarginPriceEconomy, this.departureTime, this.seatsEconomy);
		}
		System.out.println(this.priceEconomy);
		System.out.println(this.departureTime);
		System.out.println(this.seatsEconomy);
		return discounts.calculatePrice(this.priceEconomy * 1.1, this.departureTime, this.seatsEconomy);
	}

	public Double calculatePriceBusiness() {
		if (discounts == null) {
			return this.priceBusiness * 1.1;
		}
		if (this.customMarginPriceBusiness != null) {
			return discounts.calculatePrice(this.customMarginPriceBusiness, this.departureTime, this.seatsBusiness);
		}
		return discounts.calculatePrice(this.priceBusiness * 1.1, this.departureTime, this.seatsBusiness);
	}

	public Double calculatePriceFirstClass() {
		if (discounts == null) {
			return this.priceFirstClass * 1.1;
		}
		if (this.customMarginPriceFirstClass != null) {
			return discounts.calculatePrice(this.customMarginPriceFirstClass, this.departureTime, this.seatsFirstClass);
		}
		return discounts.calculatePrice(this.priceFirstClass * 1.1, this.departureTime, this.seatsFirstClass);
	}

	public Double getCustomMarginPriceEconomy() {
		return customMarginPriceEconomy;
	}

	public void setCustomMarginPriceEconomy(Double customMarginPriceEconomy) {
		this.customMarginPriceEconomy = customMarginPriceEconomy;
	}

	public Double getCustomMarginPriceBusiness() {
		return customMarginPriceBusiness;
	}

	public void setCustomMarginPriceBusiness(Double customMarginPriceBusiness) {
		this.customMarginPriceBusiness = customMarginPriceBusiness;
	}

	public Double getCustomMarginPriceFirstClass() {
		return customMarginPriceFirstClass;
	}

	public void setCustomMarginPriceFirstClass(Double customMarginPriceFirstClass) {
		this.customMarginPriceFirstClass = customMarginPriceFirstClass;
	}
}
