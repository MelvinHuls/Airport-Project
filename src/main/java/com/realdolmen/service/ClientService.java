package com.realdolmen.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.mindrot.jbcrypt.BCrypt;

import com.realdolmen.beans.FlightSearchBean;
import com.realdolmen.domain.Client;
import com.realdolmen.repository.ClientRepository;

//@EJB(name="java:global/RAir/ClientService", beanInterface = SessionRemote.class, beanName="ClientService")
@Remote
@Stateless
@LocalBean
public class ClientService implements SessionRemote, AbstractService<Client> {
	private Client client;

	@Inject
	private ClientRepository cRepo;

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

	public Client findByEmail(String email) {
		return cRepo.findByEmail(email);
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

	private boolean clientExistsCheck(String email) {
		Client customerByEmail = findByEmail(email);
		if (customerByEmail == null) {
			return false;
		}
		return true;
	}

	public boolean validate(String email, String password) {

		Client c = cRepo.findByEmail(email);
		if (c.getId() != null) {
			if (checkPassword(password, c.getPassword())) {
				return true;
			}
		}
		return false;
	}

	public boolean checkPassword(String password, String hashed) {
		try {
			return BCrypt.checkpw(password, hashed);
		} catch (NullPointerException e) {
			return false;
		}
	}

	public String hashPassword(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt(24));
	}
}
