package com.realdolmen.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.realdolmen.enumerations.FlightClass;

@FacesConverter(forClass = FlightClass.class)
public class FlightClassesConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null || value.equals("")) {
			return null;
		}

		FlightClass[] classes = FlightClass.values();
		for (int i = 0; i < classes.length; i++) {
			if (classes[i].toString().equals(value))
				return classes[i];
		}

		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return value.toString();
	}

}
