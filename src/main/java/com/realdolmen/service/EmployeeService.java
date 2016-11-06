package com.realdolmen.service;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;

import com.realdolmen.domain.Employee;

//@EJB(name="java:global/RAir/EmployeeService", beanInterface = SessionRemote.class, beanName="EmployeeService")
@Stateful
@LocalBean
public class EmployeeService implements SessionRemote {
	private Employee employee;

	public EmployeeService() {
		super();
	}

	public EmployeeService(Employee employee) {
		super();
		this.employee = employee;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
}
