package com.realdolmen.converters;

import java.util.Iterator;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import com.realdolmen.domain.GlobalRegion;

@FacesConverter(forClass = GlobalRegion.class)
public class GlobalRegionConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		System.out.println("getasobject");
		if (value == null || value.equals("")) {
			return null;
		}

		String[] regionComp = value.split(" ");
		String regionName = "";
		Boolean first = true;
		for (int j = 0; j < regionComp.length; j++) {
			if (first) {
				regionName += regionComp[j];
				first = false;
			} else {
				regionName += "_" + regionComp[j];
			}
		}

		GlobalRegion[] regions = GlobalRegion.values();
		for (int i = 0; i < regions.length; i++) {
			if (regions[i].toString().equals(regionName))
				return regions[i];
		}

		return null;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value == null) {
			return null;
		}

		/*if (value instanceof List<?>) {
			System.out.println("list");
			StringBuffer result = new StringBuffer();
			List<?> list = (List<?>) value;
			Iterator<?> it = list.iterator();
			Boolean first = true;
			while (it.hasNext()) {
				GlobalRegion region = (GlobalRegion) it.next();

				if (!first) {
					result.append(", ");
				} else {
					first = false;
				}
				result.append(this.toGlobalRegionString(region));
			}
			System.out.println(result.toString());
			return result.toString();
		}*/

		return this.toGlobalRegionString(value);
	}

	private String toGlobalRegionString(Object value) {
		String[] regionComp = value.toString().split("_");
		String regionName = "";
		Boolean first = true;
		for (int j = 0; j < regionComp.length; j++) {
			if (first) {
				regionName += regionComp[j];
				first = false;
			} else {
				regionName += " " + regionComp[j];
			}
		}
		return regionName;
	}

}
