package com.realdolmen.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.realdolmen.domain.Client;
import com.realdolmen.domain.Employee;
import com.realdolmen.domain.Partner;
import com.realdolmen.service.ClientService;
import com.realdolmen.service.EmployeeService;
import com.realdolmen.service.PartnerService;

@SessionScoped
@Stateful
@Remote
@Named("registrationBean")
public class RegistrationBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@EJB
	ClientService clientService;

	@EJB
	PartnerService partnerService;

	@EJB
	EmployeeService employeeService;

	private Partner partner;
	private Employee employee;
	private Client client;

	@PostConstruct
	public void init() {
		clientService = new ClientService();
		partnerService = new PartnerService();
		employeeService = new EmployeeService();
		partner = new Partner();
		employee = new Employee();
		client = new Client();
	}

	// private String email, password;

	public String registerClient() {
		if (this.client == null) {
			return "Registration failed, step 1";
		} else {
			if (userDoesntExist(client.getEmail())) {
				clientService.create(client);
				return "Client registered";
			} else {
				return "Registration failed, step 2";
			}
		}
	}

	public String registerEmployee() {
		if (this.employee == null) {
			return "Registration failed, step 1";
		} else {
			if (userDoesntExist(employee.getEmail())) {
				employeeService.create(employee);
				return "Employee registered";
			} else {
				return "Registration failed, step 2";
			}
		}
	}

	public String registerPartner() {
		if (this.partner == null) {
			return "Registration failed, step 1";
		} else {
			if (userDoesntExist(partner.getEmail())) {
				partnerService.create(partner);
				return "Partner registered";
			} else {
				return "Registration failed, step 2";
			}
		}
	}

	private boolean userDoesntExist(String email) {
		Client client = clientService.findByEmail(email);
		Employee employee = employeeService.findByEmail(email);
		Partner partner = partnerService.findByEmail(email);
		if (employee.getId() == null && client.getId() == null && partner.getId() == null) {
			return true;
		} else {
			return false;
		}
	}

	public List<String> getRoles() {
		List<String> roles = new ArrayList<>();
		roles.add("Client");
		roles.add("Employee");
		roles.add("Partner");
		return roles;
	}

	public ClientService getClientService() {
		return clientService;
	}

	public PartnerService getPartnerService() {
		return partnerService;
	}

	public EmployeeService getEmployeeService() {
		return employeeService;
	}
}