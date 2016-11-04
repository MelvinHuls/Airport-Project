package com.realdolmen.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Location {

	@Id
	@GeneratedValue
	private long id;
	private String airport;
	private String country;
	private String code;
	private GlobalRegion region;

	public Location() {
	}

	public Location(String airport, String country, String code, GlobalRegion region) {
		this.airport = airport;
		this.country = country;
		this.code = code;
		this.region = region;
	}

	public String getAirport() {
		return airport;
	}

	public void setAirport(String airport) {
		this.airport = airport;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public GlobalRegion getRegion() {
		return region;
	}

	public void setRegion(GlobalRegion region) {
		this.region = region;
	}

	public long getId() {
		return id;
	}

}
