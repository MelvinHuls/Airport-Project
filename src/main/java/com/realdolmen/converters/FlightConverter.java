package com.realdolmen.converters;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.realdolmen.beans.FlightBean;
import com.realdolmen.domain.Flight;

@FacesConverter(value="FlightConverter")
public class FlightConverter implements Converter{
	@EJB
	  FlightBean fbean;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {		
		return fbean.getFlight(Long.parseLong(value, 10));
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return ((Flight) value).getId().toString();
	}
}
