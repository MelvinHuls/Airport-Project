package com.realdolmen.beans;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;

import com.realdolmen.domain.Booking;
import com.realdolmen.repository.BookingRepository;

@Stateless
@ManagedBean
public class BookingDetails {
	private String bookingId;
	private String userId;
	private Booking booking;
	
	@EJB
	BookingRepository brepo;
	
	//do something directly after id has been set
	@PostConstruct
	public void init() {
	    booking = brepo.read(Long.parseLong(bookingId, 10));
	    if(!userId.equals(booking.getClient().getId().toString())) {
	    	System.out.println("not the correct user, whipe booking");
	    	System.out.println("given user " + userId + " user who made the booking " + booking.getClient().getId());
	    	booking = null;
	    }
	}
	
	
	public String getBookingId() {
		return bookingId;
	}
	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Booking getBooking() {
		return booking;
	}
	public void setBooking(Booking booking) {
		this.booking = booking;
	}
	
	
	
}
