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

		String[] classComp = value.split(" ");
		String className = "";
		Boolean first = true;
		for (int j = 0; j < classComp.length; j++) {
			if (first) {
				className += classComp[j];
				first = false;
			} else {
				className += "_" + classComp[j];
			}
		}

		FlightClass[] classes = FlightClass.values();
		for (int i = 0; i < classes.length; i++) {
			if (classes[i].toString().equals(className))
				return classes[i];
		}

		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value == null) {
			return null;
		}
		return this.toFlightClassString(value);
	}

	private String toFlightClassString(Object value) {
		String[] classComp = value.toString().split("_");
		String className = "";
		Boolean first = true;
		for (int j = 0; j < classComp.length; j++) {
			if (first) {
				className += classComp[j];
				first = false;
			} else {
				className += " " + classComp[j];
			}
		}
		return className;
	}

}
