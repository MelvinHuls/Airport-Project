package com.realdolmen.service;

import java.util.Date;
import java.util.Properties;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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
				String result = this.bookFlight.bookFlightCreditCard(outwardFlight, creditcardNumber, expirationDate,
						flightclass, seats);
				 sendMail(this.bookFlight.getBooking().getClient().getEmail(),
				 "Booking RAir", this.bookFlight.getBooking().toemail());
				return result;
			} else {
				String result = this.bookFlight.bookFlightCreditCard(outwardFlight, returnFlight, creditcardNumber,
						expirationDate, flightclass, seats);
				 sendMail(this.bookFlight.getBooking().getClient().getEmail(),
				 "Booking RAir", this.bookFlight.getBooking().toemail());
				return result;
			}
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
			return "failure";
		}
	}

	public String BookFlightEndorsement(FlightClass flightclass, Integer seats)
			throws NotEnoughSeatsException, LackingPricingInformationException, AddressException, MessagingException {
		if (bookFlight == null) {
			bookFlight = new BookFlightBean();
		}
		if (returnFlight == null) {
			String result = this.bookFlight.bookFlightEndorsement(outwardFlight, flightclass, seats);
			sendMail(this.bookFlight.getBooking().getClient().getEmail(), "Booking RAir",
					this.bookFlight.getBooking().toemail());
			return result;
		} else {
			String result = this.bookFlight.bookFlightEndorsement(outwardFlight, returnFlight, flightclass, seats);
			sendMail(this.bookFlight.getBooking().getClient().getEmail(), "Booking RAir",
					this.bookFlight.getBooking().toemail());
			return result;
		}
	}

	private void sendMail(String recipientEmail, String title, String body)
			throws AddressException, MessagingException {
		// Sender's email ID needs to be mentioned
		final String username = "realdolmenairline@gmail.com";
		final String password = "RealdolmenAirport";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(recipientEmail));
			message.setSubject(title);
			message.setText(body);

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
