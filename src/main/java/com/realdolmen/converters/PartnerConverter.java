package com.realdolmen.converters;

import javax.ejb.EJB;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.realdolmen.beans.PartnerBean;
import com.realdolmen.domain.Partner;

@FacesConverter(value="FlightConverter")
public class PartnerConverter implements Converter{
	@EJB
	PartnerBean pbean;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return pbean.getPartner(Long.parseLong(value, 10));
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {		
		return Long.toString(((Partner) value).getId());
	}
	
}
