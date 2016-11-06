package com.realdolmen.domain;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class Partner extends User {
	@NotNull
	private String company;

	public Partner() {
	}

	public Partner(String company) {
		super();
		this.company = company;
	}

	public Partner(String username, String password, String email, String company) {
		super(username, password, email);
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
