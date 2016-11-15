package com.realdolmen.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Employee")
public class Employee extends User {
	public Employee() {
		super();
	}

	public Employee(String un, String pw, String em) {
		super(un, pw, em);
	}
}
