package com.realdolmen.enumerations;

public enum GlobalRegion {
	WESTERN_EUROPE, EASTERN_EUROPE, NORTH_AMERICA, MIDDLE_AMERICA, SOUTH_AMERICA;

	@Override
	public String toString() {
		switch (this) {
		case WESTERN_EUROPE:
			return "Western Europe";
		case EASTERN_EUROPE:
			return "Eastern Europe";
		case NORTH_AMERICA:
			return "North America";
		case MIDDLE_AMERICA:
			return "Middle America";
		case SOUTH_AMERICA:
			return "South America";
		default:
			throw new IllegalArgumentException();
		}
	}
}