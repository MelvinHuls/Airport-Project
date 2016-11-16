package com.realdolmen.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Client")
public class Client extends User {
	public Client() {
		super();
	}

	public Client(String username, String password, String email) {
		super(username, password, email);
	}
}
