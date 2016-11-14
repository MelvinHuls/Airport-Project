package com.realdolmen.beans;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.mindrot.jbcrypt.BCrypt;

import com.realdolmen.domain.Booking;
import com.realdolmen.domain.Client;
import com.realdolmen.service.ClientService;

@Named
@SessionScoped
public class ClientBean implements Serializable {
	private static final long serialVersionUID = 5733569327636548324L;

	@Inject
	ClientService clientService;

	Booking booking;

	private Client client;
	private String password= "123456";
	private Client loggedInClient;
	private String loginErrorMessage;
	private String registerErrorMessage;

	@PostConstruct
	public void init() {
		client = new Client();
		client.setUsername("Debby");
		loggedInClient = null;
	}

	public String register() {
		try {
			if (clientService.findByEmail(client.getEmail()) == null) {
				clientService.create(client);
				cleanclient();
				registerErrorMessage = "";
				loginErrorMessage = "";
				return "success";
			} else {
				registerErrorMessage = "E-mail already exists.";
				return "failure";
			}
		} catch (Exception e) {
			registerErrorMessage = "Something went wrong.";
			e.printStackTrace();
			return "failure";
		}
	}

	public String login() {
		try {
			client = clientService.findByEmail(client.getEmail());
			if (BCrypt.checkpw(password, client.getPassword())) {
				loggedInClient = client;
				loginErrorMessage = "";
				registerErrorMessage = "";
				if (booking.getOutgoing() != null) {
					return "flights";
				} else {
					return "success";
				}
			} else {
				loginErrorMessage = "Password is incorrect.";
				return "failure";
			}
		} catch (Exception e) {
			e.printStackTrace();
			loginErrorMessage = "E-mail is incorrect.";
			return "failure";
		}
	}

	public String logout() {
		cleanclient();
		return "flights";
	}

	public void cleanclient() {
		client = new Client();
		client.setUsername("");
		loggedInClient = null;
	}

	public String getLoginErrorMessage() {
		return loginErrorMessage;
	}

	public void setLoginErrorMessage(String loginErrorMessage) {
		this.loginErrorMessage = loginErrorMessage;
	}

	public String getRegisterErrorMessage() {
		return registerErrorMessage;
	}

	public void setRegisterErrorMessage(String registerErrorMessage) {
		this.registerErrorMessage = registerErrorMessage;
	}

	public Client getLoggedInclient() {
		return loggedInClient;
	}

	public void setLoggedInclient(Client loggedInclient) {
		this.loggedInClient = loggedInclient;
	}

	public Client getclient() {
		return client;
	}

	public void setclient(Client client) {
		this.client = client;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String goToLogin() {
		return "login";
	}

	public String goToRegistration() {
		cleanclient();
		return "register";
	}
}