package com.realdolmen.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import com.mysql.fabric.xmlrpc.base.Array;
import com.realdolmen.enumerations.GlobalRegion;

@Entity
public class Location {
	@Id
	@GeneratedValue
	private Long id;
	@NotNull
	private String airport;
	@NotNull
	private String country;
	@NotNull
	private String code;
	@NotNull
	@Enumerated(EnumType.STRING)
	private GlobalRegion region;

	public Location() {
	}

	public Location(String airport, String country, String code, GlobalRegion region) {
		this.airport = airport;
		this.country = country;
		this.code = code;
		this.region = region;
		// this.regionName = region.toString();
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

	public void setRegion(String region) {
		this.region = GlobalRegion.valueOf(region);
	}

	public Long getId() {
		return id;
	}
	
	public List<GlobalRegion> getRegions() {
		return Arrays.asList(GlobalRegion.values());
	}
}
