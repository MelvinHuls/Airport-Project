package com.realdolmen.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import com.realdolmen.Exceptions.LackingPricingInformationException;
import com.realdolmen.Exceptions.NotEnoughSeatsException;
import com.realdolmen.enumerations.FlightClass;

@Entity
public class Flight {
	@Id
	@GeneratedValue
	private Long id;
	@Version
	private Integer version;

	@NotNull
	private Integer seatsEconomy;
	@NotNull
	private Integer seatsBusiness;
	@NotNull
	private Integer seatsFirstClass;

	@NotNull
	private Double priceEconomy;
	@NotNull
	private Double priceBusiness;
	@NotNull
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
		if (discounts.getCompany() == this.company) {
			this.discounts = discounts;
		}
	}

	public Double calculatePriceOneSeatEconomy() {
		return this.calculateSeatPrice(this.priceEconomy, this.customMarginPriceEconomy);
	}

	public Double calculatePriceOneSeatBusiness() {
		return this.calculateSeatPrice(this.priceBusiness, this.customMarginPriceBusiness);
	}

	public Double calculatePriceOneSeatFirstClass() {
		return this.calculateSeatPrice(this.priceFirstClass, this.customMarginPriceFirstClass);
	}

	private Double calculateSeatPrice(Double basePrice, Double customMarginPrice) {
		if (customMarginPrice != null) {
			if (this.departureTime != null && discounts != null) {
				return discounts.calculateSeatPrice(customMarginPrice, this.departureTime);
			} else {
				System.out.println("discounts " + discounts);
				System.out.println("dep time " + this.departureTime);
				return customMarginPrice;
			}
		} else if (basePrice == null) {
			return null;
		} else if (discounts == null || this.departureTime == null) {
			return basePrice * 1.1;
		}

		return discounts.calculateSeatPrice(basePrice * 1.1, this.departureTime);
	}

	public Double calculateTotalPriceClass(FlightClass flightClass, Integer seats)
			throws LackingPricingInformationException {
		if (flightClass.equals(FlightClass.ECONOMY)) {
			return calculateTotalPriceEconomy(seats);
		} else if (flightClass.equals(FlightClass.BUSINESS)) {
			return calculateTotalPriceBusiness(seats);
		} else if (flightClass.equals(FlightClass.FIRST_CLASS)) {
			return calculateTotalPriceFirstClass(seats);
		}
		return 0d;
	}

	public Double calculateTotalPriceEconomy(Integer seats) throws LackingPricingInformationException {
		return this.calculateTotalPrice(this.priceEconomy, this.customMarginPriceEconomy, seats);
	}

	public Double calculateTotalPriceBusiness(Integer seats) throws LackingPricingInformationException {
		return this.calculateTotalPrice(this.priceBusiness, this.customMarginPriceBusiness, seats);
	}

	public Double calculateTotalPriceFirstClass(Integer seats) throws LackingPricingInformationException {
		return this.calculateTotalPrice(this.priceFirstClass, this.customMarginPriceFirstClass, seats);
	}

	private Double calculateTotalPrice(Double basePrice, Double customMarginPrice, Integer seats)
			throws LackingPricingInformationException {
		if (seats == null || seats == 0)
			return 0d;

		if (customMarginPrice != null) {
			if (this.departureTime != null && discounts != null) {
				return discounts.calculatePrice(customMarginPrice, this.departureTime, seats);
			} else {
				return customMarginPrice * seats;
			}
		} else if (basePrice == null) {
			throw new LackingPricingInformationException();
		} else if (discounts == null || this.getDepartureTime() == null) {
			return basePrice * 1.1 * seats;
		}

		return discounts.calculatePrice(basePrice * 1.1, this.departureTime, seats);
	}

	public Double calculateDiscountClass(FlightClass flightClass, Integer seats) {
		if (flightClass.equals(FlightClass.ECONOMY)) {
			return calculateDiscountEconomy(seats);
		} else if (flightClass.equals(FlightClass.BUSINESS)) {
			return calculateDiscountBusiness(seats);
		} else if (flightClass.equals(FlightClass.FIRST_CLASS)) {
			return calculateDiscountFirstClass(seats);
		}
		return 0d;
	}

	public Double calculateDiscountEconomy(Integer seats) {
		return this.calculateDiscount(this.priceEconomy, this.customMarginPriceEconomy, seats);
	}

	public Double calculateDiscountBusiness(Integer seats) {
		return this.calculateDiscount(this.priceBusiness, this.customMarginPriceBusiness, seats);
	}

	public Double calculateDiscountFirstClass(Integer seats) {
		return this.calculateDiscount(this.priceFirstClass, this.customMarginPriceFirstClass, seats);
	}

	private Double calculateDiscount(Double basePrice, Double customMarginPrice, Integer seats) {
		if (seats == null || seats == 0)
			return 0d;

		if (customMarginPrice != null) {
			if (this.departureTime != null && discounts != null) {
				return discounts.calculateDiscount(customMarginPrice, this.departureTime, seats);
			} else {
				return 0d;
			}
		} else if (basePrice == null) {
			return 0d;
		} else if (discounts == null || this.getDepartureTime() == null) {
			return 0d;
		}

		return discounts.calculateDiscount(basePrice * 1.1, this.departureTime, seats);
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

	@Override
	public String toString() {
		String string = "";
		string += "company name: " + this.company + "\n";
		string += "id: " + this.id + "\n";
		string += "seatsEconomy: " + this.seatsEconomy + "\n";
		string += "seatsBusiness: " + this.seatsBusiness + "\n";
		string += "seatsFirstClass: " + this.seatsFirstClass + "\n";
		string += "priceEconomy: " + this.priceEconomy + "\n";
		string += "priceBusiness: " + this.priceBusiness + "\n";
		string += "priceFirstClass: " + this.priceFirstClass + "\n";
		string += "customMarginPriceEconomy: " + this.customMarginPriceEconomy + "\n";
		string += "customMarginPriceBusiness: " + this.customMarginPriceBusiness + "\n";
		string += "customMarginPriceFirstClass: " + this.customMarginPriceFirstClass + "\n";
		string += "departure: " + this.departure + "\n";
		string += "destination: " + this.destination + "\n";
		string += "departureTime: " + this.departureTime + "\n";
		string += "duration: " + this.duration + "\n";

		return string;
	}

	public String tomail(FlightClass flightclass) {
		String string = "";
		string += "company name: " + this.company + "\n";
		switch (flightclass) {
		case FIRST_CLASS:
			if (customMarginPriceFirstClass != null) {
				string += "Base price one seat:  " + this.customMarginPriceFirstClass + "\n";
			} else {
				string += "Base price one seat:  " + this.priceFirstClass + "\n";
			}
			break;
		case ECONOMY:
			if (customMarginPriceEconomy != null) {
				string += "Base price one seat:  " + this.customMarginPriceEconomy + "\n";
			} else {
				string += "Base price one seat:  " + this.priceEconomy + "\n";
			}
			break;
		case BUSINESS:
			if (customMarginPriceBusiness != null) {
				string += "Base price one seat:  " + this.customMarginPriceBusiness + "\n";
			} else {
				string += "Base price one seat:  " + this.priceBusiness + "\n";
			}
			break;
		}
		string += "------ departure ------\n" + this.departure + "\n";
		string += "------ destination ------\n" + this.destination + "\n";
		string += "departureTime:  " + this.departureTime + "\n";
		string += "duration:  " + this.duration.getHours() + " uren en " + this.duration.getMinutes() + " minuten\n";

		return string;
	}

	public Integer getVersion() {
		return version;
	}

	public void bookSeats(FlightClass flightclass, Integer seats) throws NotEnoughSeatsException {
		switch (flightclass) {
		case ECONOMY:
			if (this.seatsEconomy < seats) {
				throw new NotEnoughSeatsException();
			}
			this.seatsEconomy -= seats;
			break;
		case BUSINESS:
			if (this.seatsBusiness < seats) {
				throw new NotEnoughSeatsException();
			}
			this.seatsBusiness -= seats;
			break;
		case FIRST_CLASS:
			if (this.seatsFirstClass < seats) {
				throw new NotEnoughSeatsException();
			}
			this.seatsFirstClass -= seats;
			break;
		}
	}
}
