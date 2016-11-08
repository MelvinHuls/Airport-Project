package com.realdolmen.domain;

import javax.persistence.Entity;

@Entity
public class Employee extends User {
	protected Employee() {
		super();
	}
	public Employee(String un, String pw, String em){
		super(un, pw, em);
	}
	
	

}
