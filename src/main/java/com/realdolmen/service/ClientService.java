package com.realdolmen.service;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;

import com.realdolmen.domain.Client;
//@EJB(name="java:global/RAir/ClientService", beanInterface = SessionRemote.class, beanName="ClientService")
@Stateful
@LocalBean
public class ClientService implements SessionRemote{
	private Client client;

	public ClientService() {
		super();
	}

	public ClientService(Client client) {
		super();
		this.client = client;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
}
