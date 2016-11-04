package com.realdolmen.domain;

import javax.persistence.Entity;

@Entity
public class Partner extends User {
	private String company;

	public Partner() {
	}

	public Partner(String company) {
		super();
		this.company = company;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public boolean changeDepartureTime() {
		System.out.println("the depaturetime has been changed");
		return true;
	}
}
