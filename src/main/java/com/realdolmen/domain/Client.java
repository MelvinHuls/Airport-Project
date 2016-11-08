package com.realdolmen.domain;

import javax.persistence.Entity;

@Entity
public class Client extends User {
	public Client() {
		super();
	}

	public Client(String username, String password, String email) {
		super(username, password, email);
	}
}
