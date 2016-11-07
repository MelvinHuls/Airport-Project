package com.realdolmen.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Location {
	@Id
	@GeneratedValue
	private long id;
	@NotNull
	private String airport;
	@NotNull
	private String country;
	@NotNull
	private String code;
	@NotNull
	//todo: add enumeration annotation
	private GlobalRegion region; 
	
	private String regionname;

	public Location() {
	}

	public Location(String airport, String country, String code, GlobalRegion region) {
		this.airport = airport;
		this.country = country;
		this.code = code;
		this.region = region;
		//this.regionName = region.toString();
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
	
	public String getNameRegion() {
		return region.toString();
	}

	public void setRegion(GlobalRegion region) {
		this.region = region;
	}
	
	public void setRegion(String region) {		
		this.region = GlobalRegion.valueOf(region);
	}
	
	//convert regionName to region enum
	public void setRegion() {		
		this.region = GlobalRegion.valueOf(regionname);
	}

	public long getId() {
		return id;
	}
	
	public List<String> getRegions() {
		GlobalRegion[] regions = GlobalRegion.values();
		List<String> regionNames = new ArrayList<String>();

		for(int i =0; i < regions.length; i++){
			regionNames.add(regions[i].toString());
		}
		return regionNames;
	}

	public String getRegionname() {
		return regionname;
	}

	public void setRegionname(String regionname) {
		this.regionname = regionname;
	}
	
	
	

}
