package com.realdolmen.service;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;

import com.realdolmen.domain.Client;
import com.realdolmen.repository.ClientRepository;

//@EJB(name="java:global/RAir/ClientService", beanInterface = SessionRemote.class, beanName="ClientService")
@Stateful
@LocalBean
public class ClientService implements SessionRemote {
	private Client client;

	private ClientRepository cRepo;

	public void create(Client client) {
		cRepo.create(client);
	}

	public Client read(Long id) {
		return cRepo.read(id);
	}

	public void update(Client client) {
		cRepo.update(client);
	}

	public void delete(Client client) {
		cRepo.delete(client);
	}

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
