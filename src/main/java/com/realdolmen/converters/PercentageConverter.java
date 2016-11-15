package com.realdolmen.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value="percentageConverter")
public class PercentageConverter implements Converter{

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		value.replace("%", "");
		Double result = Double.parseDouble(value);
		result /= 100;
		return result;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		Double number = (Double) value;
		number *= 100;
		int roundNum = number.intValue();
		return String.valueOf(roundNum) + "%";
	}
}
