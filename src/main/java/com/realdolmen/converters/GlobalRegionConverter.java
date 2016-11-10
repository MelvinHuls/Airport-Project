package com.realdolmen.converters;

import java.util.Iterator;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.realdolmen.enumerations.GlobalRegion;

@FacesConverter(forClass = GlobalRegion.class)
public class GlobalRegionConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null || value.equals("")) {
			return null;
		}

		GlobalRegion[] regions = GlobalRegion.values();
		for (int i = 0; i < regions.length; i++) {
			if (regions[i].toString().equals(value))
				return regions[i];
		}
		
		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return value.toString();
	}

}
