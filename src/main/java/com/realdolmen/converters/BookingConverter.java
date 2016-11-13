package com.realdolmen.converters;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.realdolmen.domain.Booking;
import com.realdolmen.repository.BookingRepository;

@FacesConverter(value = "bookingConverter")
public class BookingConverter implements Converter {
	@EJB
	BookingRepository brepo;

	Booking booking;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (booking == null) {
			booking = brepo.read(Long.parseLong(value, 10));
		}
		return booking;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return ((Booking) value).getId().toString();
	}
}
