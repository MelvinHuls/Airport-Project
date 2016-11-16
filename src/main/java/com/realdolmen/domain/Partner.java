package com.realdolmen.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
@DiscriminatorValue("Partner")
public class Partner extends User {
	@NotNull
	private String company;

	public Partner() {
		super();
	}

	public Partner(String username, String password, String email, String company) {
		super(username, password, email);
		this.company = company;
	}

	public Partner(String username, String password, String email) {
		super(username, password, email);
		this.company = null;
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
