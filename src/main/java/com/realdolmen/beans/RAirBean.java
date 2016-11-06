package com.realdolmen.beans;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;

import com.realdolmen.domain.Client;
import com.realdolmen.domain.Employee;
import com.realdolmen.domain.Partner;
import com.realdolmen.domain.User;
import com.realdolmen.service.ClientService;
import com.realdolmen.service.EmployeeService;
import com.realdolmen.service.PartnerService;

@SessionScoped
@Stateful
@Remote
@Named("rAirBean")
public class RAirBean {
	@PersistenceContext
	private EntityManager em;

	@EJB
	private PartnerService partnerService;
	
	@EJB
	private ClientService clientService;
	
	@EJB
	private EmployeeService employeeService;
		
	public void logOn(String username, String password) throws NonUniqueResultException {
		try {
			Class<? extends User> kind = Partner.class;
			Partner partner = (Partner) searchUser(kind, username, password);
			if (partner != null) {
				partnerService.setPartner(partner);
				clientService.setClient(null);
				employeeService.setEmployee(null);
				System.out.println("partner session made");
			} else {
				kind = Client.class;
				Client client = (Client) searchUser(kind, username, password);
				if (client != null) {
					clientService.setClient(client);
					partnerService.setPartner(partner);
					employeeService.setEmployee(null);
					System.out.println("client session made");
				} else {
					kind = Employee.class;
					Employee employee = (Employee) searchUser(kind, username, password);
					if (employee != null) {
						employeeService.setEmployee(employee);
						partnerService.setPartner(partner);
						clientService.setClient(null);
						System.out.println("employee session made");
					}
				}
			}
		} catch (NoResultException ex) {
			System.out.println("No user found with username " + username);
		}
	}

	/*public void testingFunctionality() {
		if (session == null) {
			System.out.println("user has not logged in");
		} else {
			session.testFunctionality();
		}
	}*/

	public <T extends User> T searchUser(Class<T> kind, String username, String password)
			throws NonUniqueResultException {
		List<T> partners = em.createQuery(
				"select u from " + kind.getSimpleName() + " u where u.username = :name AND u.password = :pass", kind)
				.setParameter("name", username).setParameter("pass", password).getResultList();
		if (partners.isEmpty()) {
			return null;
		} else if (partners.size() > 1) {
			throw new NonUniqueResultException("There is more then one user with the same username");
		}

		return partners.get(0);
	}

	public PartnerService getPartnerService() {
		return partnerService;
	}

	public ClientService getClientService() {
		return clientService;
	}

	public EmployeeService getEmployeeService() {
		return employeeService;
	}
	
	
}
