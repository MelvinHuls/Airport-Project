package com.realdolmen.beans;

import java.util.Date;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.faces.bean.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;

import com.realdolmen.Exceptions.LackingPaymentInformationException;
import com.realdolmen.Exceptions.LackingPricingInformationException;
import com.realdolmen.Exceptions.NotEnoughSeatsException;
import com.realdolmen.domain.Booking;
import com.realdolmen.domain.Flight;
import com.realdolmen.domain.User;
import com.realdolmen.enumerations.FlightClass;
import com.realdolmen.enumerations.PaymentStatus;

//@RequestScoped
@SessionScoped
@Remote
@Stateless
public class BookFlightBean {
	@PersistenceContext
	private EntityManager em;

	private Boolean creditCard;
	
	private Booking booking;

	public String bookFlightCreditCard(/* User user, */ Flight outgoingFlight, Flight returnFlight,
			String creditcardNumber, Date expirationDate, FlightClass flightclass, Integer seats)
			throws NotEnoughSeatsException, LackingPricingInformationException {
		/* if(user.class == client) { */
		booking = new Booking(em.createQuery("select u from User u", User.class).getResultList().get(0), outgoingFlight, returnFlight,
				PaymentStatus.PAYED_CREDIT_CARD, flightclass, seats);
		booking.enterCreditCardInformation(creditcardNumber, expirationDate);

		// always execute after filling in the credit card information
		try {
			booking.calculateAndSetTotalPrice();
		} catch (LackingPaymentInformationException ex) {
			return "failure";
		}

		try {
			// tx.begin();
			outgoingFlight.bookSeats(flightclass, seats);
			em.merge(outgoingFlight);
			returnFlight.bookSeats(flightclass, seats);
			em.merge(returnFlight);
			em.persist(booking);
			// tx.commit();
		} catch (Exception ex) { // check on optimisticLockexception
			return "failure";
		}

		return "success";

		/* } else { throw new Exception;} */
	}

	public String bookFlightCreditCard(/* User user, */ Flight outgoingFlight, String creditcardNumber,
			Date expirationDate, FlightClass flightclass, Integer seats)
			throws NotEnoughSeatsException, LackingPricingInformationException {
		/* if(user.class == client) { */
		// Flight outgoingFlight = em.find(Flight.class, outgoingFlightId);

		booking = new Booking(em.createQuery("select u from User u", User.class).getResultList().get(0),
				outgoingFlight, PaymentStatus.PAYED_CREDIT_CARD, flightclass, seats);
		booking.enterCreditCardInformation(creditcardNumber, expirationDate);

		// always execute after filling in the credit card information
		try {
			booking.calculateAndSetTotalPrice();
		} catch (LackingPaymentInformationException ex) {
			return "failure";
		}
		System.out.println("outgoingflight Id " + outgoingFlight.getId());
		// EntityTransaction tx = em.getTransaction();

		try {
			// em.getTransaction().begin();
			outgoingFlight.bookSeats(flightclass, seats);
			em.merge(outgoingFlight);
			em.persist(booking);
			// em.getTransaction().commit();
		} catch (Exception ex) { // check on optimisticLockexception
			System.err.println(ex.getMessage());
			return "failure";
		}

		return "success";
		/* } else { throw new Exception;} */
	}

	public String bookFlightEndorsement(/* User user, */ Flight outgoingFlight, FlightClass flightclass, Integer seats)
			throws NotEnoughSeatsException, LackingPricingInformationException {
		/* if(user.class == client) { */

		booking = new Booking(em.createQuery("select u from User u", User.class).getResultList().get(0), outgoingFlight, PaymentStatus.PAYMENT_PENDING, flightclass,
				seats);

		// always execute after filling in the credit card information
		try {
			booking.calculateAndSetTotalPrice();
		} catch (LackingPaymentInformationException ex) {
			return "failure";
		}

		try {
			outgoingFlight.bookSeats(flightclass, seats);
			em.merge(outgoingFlight);
			em.persist(booking);
		} catch (Exception ex) { // check on optimisticLockexception
			return "failure";
		}

		return "success";

		/* } else { throw new Exception;} */
	}

	public String bookFlightEndorsement(/* User user, */ Flight outgoingFlight, Flight returnFlight,
			FlightClass flightclass, Integer seats) throws NotEnoughSeatsException, LackingPricingInformationException {
		/* if(user.class == client) { */

		booking = new Booking(em.createQuery("select u from User u", User.class).getResultList().get(0), outgoingFlight, returnFlight, PaymentStatus.PAYMENT_PENDING,
				flightclass, seats);

		// always execute after filling in the credit card information
		try {
			booking.calculateAndSetTotalPrice();
		} catch (LackingPaymentInformationException ex) {
			return "failure";
		}

		try {
			outgoingFlight.bookSeats(flightclass, seats);
			em.merge(outgoingFlight);
			returnFlight.bookSeats(flightclass, seats);
			em.merge(returnFlight);
			em.persist(booking);
		} catch (Exception ex) { // check on optimisticLockexception
			return "failure";
		}

		return "success";

		/* } else { throw new Exception;} */
	}

	public Boolean getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(Boolean creditCard) {
		this.creditCard = creditCard;
	}
	
	public String getUrl() {
		String url = "http://localhost:8080/project-exercise-1/detailsBooking.xhtml?bookingId=";
		url += booking.getId();
		url += "&userId=";
		url += booking.getClient().getId();
		return url;
	}

}
