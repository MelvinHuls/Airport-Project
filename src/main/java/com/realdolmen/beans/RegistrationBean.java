package com.realdolmen.beans;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import com.realdolmen.domain.Client;
import com.realdolmen.service.ClientService;

@SessionScoped
@Named("registration")
public class RegistrationBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private SessionBean sessionBean;

	@Inject
	ClientService clientService;

	@NotNull
	@Email
	private String email;
	@NotNull
	@Size(min = 6, max = 255)
	private String password;

	@NotNull
	@Size(min = 2, max = 20)
	private String userName;

	private boolean editable;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public void register() {
		setEditable(!isEditable());
		Client c = clientService.findByEmail(email);
		if (c.getId() == null) {
			clientService.create(userName, password, email);
			HttpSession session = sessionBean.getSession();
			session.setAttribute("email", email);
		}
	}

	public void login() {
		setEditable(!isEditable());
		Client c = clientService.findByEmail(email);
		if (c.getId() == null) {
		}
	}
}