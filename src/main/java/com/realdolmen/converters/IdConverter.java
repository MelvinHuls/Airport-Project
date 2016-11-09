package com.realdolmen.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value="IdConverter")
public class IdConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return Long.parseLong(value, 10);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return Long.toString((Long)value);
	}
}
