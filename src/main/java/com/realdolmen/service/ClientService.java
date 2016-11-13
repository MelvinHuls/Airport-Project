package com.realdolmen.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;

import org.mindrot.jbcrypt.BCrypt;

import com.realdolmen.beans.FlightSearchBean;
import com.realdolmen.domain.Client;
import com.realdolmen.domain.Flight;
import com.realdolmen.repository.ClientRepository;

//@EJB(name="java:global/RAir/ClientService", beanInterface = SessionRemote.class, beanName="ClientService")
@Stateful
@LocalBean
public class ClientService implements SessionRemote, AbstractService<Client> {
	private Client client;

	private ClientRepository cRepo;
	
	@EJB
	private FlightSearchBean fsearch;

	@Override
	public void create(Client client) {
		this.client = client;

		if (!clientExistsCheck(client.getUsername())) {
			String passwordHash = BCrypt.hashpw(client.getPassword(), BCrypt.gensalt());
			client.setPassword(passwordHash);
			cRepo.create(client);
		}
	}

	public void create(String un, String pw, String em) {
		client = new Client(un, pw, em);
		if (clientExistsCheck(client.getUsername())) {
			String passwordHash = BCrypt.hashpw(pw, BCrypt.gensalt());
			client.setPassword(passwordHash);
			cRepo.create(client);
		}
	}

	@Override
	public Client findById(Long id) {
		return cRepo.read(id);
	}

	public List<Client> findByUserName(String userName) {
		return cRepo.findByUserName(userName);
	}

	@Override
	public String update(Client client) {
		return cRepo.update(client);
	}

	@Override
	public void delete(Client client) {
		cRepo.delete(client);
	}

	@Override
	public List<Client> findAll() {
		return cRepo.findAll();
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

	private boolean clientExistsCheck(String userName) {
		List<Client> customerByEmail = findByUserName(userName);
		if (customerByEmail.isEmpty()) {
			return false;
		}
		return true;
	}
}
