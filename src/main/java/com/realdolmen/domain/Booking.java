package com.realdolmen.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import com.realdolmen.Exceptions.LackingPaymentInformationException;
import com.realdolmen.Exceptions.LackingPricingInformationException;
import com.realdolmen.enumerations.FlightClass;
import com.realdolmen.enumerations.PaymentStatus;

@Entity
public class Booking {
	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	// @NotNull
	private User client;

	@ManyToOne
	@NotNull
	private Flight outgoingFlight;

	@ManyToOne
	private Flight returnFlight;

	@NotNull
	private Double totalPrice;

	// ENCRYPTH THIS!!!!
	private String creditcardNumber;
	private Date expirationDate;

	@NotNull
	private PaymentStatus paymentMethod;

	@NotNull
	private FlightClass flightclass;

	@NotNull
	private Integer seats;

	protected Booking() {
	}

	public Booking(User client, Flight outgoingFlight, PaymentStatus paymentMethod, FlightClass flightclass,
			Integer seats) {
		super();
		this.client = client;
		this.outgoingFlight = outgoingFlight;
		this.paymentMethod = paymentMethod;
		this.flightclass = flightclass;
		this.seats = seats;
	}

	public Booking(User client, Flight outgoingFlight, Flight returnFlight, PaymentStatus paymentMethod,
			FlightClass flightclass, Integer seats) {
		super();
		this.client = client;
		this.outgoingFlight = outgoingFlight;
		this.returnFlight = returnFlight;
		this.paymentMethod = paymentMethod;
		this.flightclass = flightclass;
		this.seats = seats;
	}

	public User getClient() {
		return client;
	}

	public void setClient(User client) {
		this.client = client;
	}

	public Flight getOutgoingFlight() {
		return outgoingFlight;
	}

	public void setOutgoingFlight(Flight outgoingFlight) {
		this.outgoingFlight = outgoingFlight;
	}

	public Flight getReturnFlight() {
		return returnFlight;
	}

	public void setReturnFlight(Flight returnFlight) {
		this.returnFlight = returnFlight;
	}

	public Long getId() {
		return id;
	}

	public void enterCreditCardInformation(String creditcardNumber, Date expirationDate) {
		this.creditcardNumber = creditcardNumber;
		this.expirationDate = expirationDate;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	// make sure to fill in credit card information before running this function
	// to get the 5% discount
	public void calculateAndSetTotalPrice()
			throws LackingPaymentInformationException, LackingPricingInformationException {
		Double CREDITCARD_DISCOUNT = 0.05;
		if (paymentMethod.equals(PaymentStatus.PAYED_CREDIT_CARD) && creditcardNumber != null
				&& expirationDate != null) {
			if (returnFlight == null) {
				this.totalPrice = outgoingFlight.calculateTotalPriceClass(this.flightclass, this.seats)
						* (1 - CREDITCARD_DISCOUNT);
			} else {
				this.totalPrice = (outgoingFlight.calculateTotalPriceClass(this.flightclass, this.seats)
						+ returnFlight.calculateTotalPriceClass(this.flightclass, this.seats))
						* (1 - CREDITCARD_DISCOUNT);
			}
		} else if (paymentMethod.equals(PaymentStatus.PAYMENT_PENDING)) {
			if (returnFlight == null) {
				this.totalPrice = outgoingFlight.calculateTotalPriceClass(this.flightclass, this.seats);
			} else {
				this.totalPrice = outgoingFlight.calculateTotalPriceClass(this.flightclass, this.seats)
						+ returnFlight.calculateTotalPriceClass(this.flightclass, this.seats);
			}
		} else {
			throw new LackingPaymentInformationException();
		}
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

	public PaymentStatus getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentStatus paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	@Override
	public String toString() {
		String out = "id  " + id + "\n";
		out += "client  " + client + "\n";
		out += "outgoingFlight  " + outgoingFlight + "\n";
		out += "returnFlight  " + returnFlight + "\n";
		out += "totalPrice   " + totalPrice + "\n";
		out += "creditcardNumber   " + creditcardNumber + "\n";
		out += "expirationDate  " + expirationDate + "\n";
		out += "paymentMethod   " + paymentMethod + "\n";
		out += "flightclass  " + flightclass + "\n";
		out += "seats  " + seats;
		
		return out;
	}
	
	public String toemail() {
		String out = "You have succesfully  booked a flight at RAir, thank you for trusting us\n\n";
		out += "You have booked " + seats + " seats in " + flightclass + "\n\n";
		out += "------ details outgoingFlight ------\n" + outgoingFlight.tomail(flightclass) + "\n-----------------------------------\n";
		if(returnFlight != null){
		out += "------ details returnFlight ------\n " + returnFlight.tomail(flightclass) + "\n-----------------------------------\n";
		}
		out += "\nTotalPrice:   " + totalPrice + "\n";
		out += "You payed using \"" + paymentMethod + "\"\n";
		out += "\nWe hope you have a pleasant flight \n\nRAir corp.";
		
		return out;
	}
	
	public Boolean returnFlightBooked() {
		if(this.returnFlight == null) {
			return false;
		}
		return true;
	}
}
