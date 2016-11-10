package com.realdolmen.enumerations;

public enum FlightClass {
	ECONOMY, BUSINESS, FIRST_CLASS;
	
	@Override
	public String toString() {
		switch (this) {
		case ECONOMY:
			return "Economy";
		case BUSINESS:
			return "Business";
		case FIRST_CLASS:
			return "First Class";
		default:
			throw new IllegalArgumentException();
		}
	}
}
