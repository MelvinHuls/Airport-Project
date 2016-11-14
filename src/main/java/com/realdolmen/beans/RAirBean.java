package com.realdolmen.beans;

import java.util.Arrays;
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
import com.realdolmen.enumerations.GlobalRegion;
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
	
	private User user = new User();

//	private boolean aClient = true;
//	private boolean anEmployee = false;
//	private boolean aPartner = false;
//
//	public void setAClient() {
//		this.aClient = true;
//		this.anEmployee = false;
//		this.aPartner = false;
//	}
//
//	public void setAnEmployee() {
//		this.aClient = false;
//		this.anEmployee = true;
//		this.aPartner = false;
//	}
//
//	public void setAPartner() {
//		this.aClient = false;
//		this.anEmployee = false;
//		this.aPartner = true;
//	}
//
//	public boolean isAClient() {
//		return aClient;
//	}
//
//	public boolean isAnEmployee() {
//		return anEmployee;
//	}
//
//	public boolean isAPartner() {
//		return aPartner;
//	}

	public String registerClient(String email, String password) {
		if (!existingUser(email)) {
			clientService.create(new Client(email, password, email));
			return "Success";
		} else {
			return "Failure";
		}
	}

	public String registerEmployee(String email, String password) {
		if (!existingUser(email)) {
			employeeService.create(new Employee(email, password, email));
			return "Success";
		} else {
			return "Failure";
		}
	}

	public String registerPartner(String email, String password, String company) {
		if (!existingUser(email)) {
			Partner client = new Partner(email, password, email, company);
			partnerService.create(client);
			return "Success";
		} else {
			return "Failure";
		}
	}

	private boolean existingUser(String email) throws NonUniqueResultException {
		Partner partner = partnerService.findByEmail(email);
		Employee employee = employeeService.findByEmail(email);
		Client client = clientService.findByEmail(email);
		if (partner == null && employee == null && client == null) {
			System.out.println("does not exists");
			return false;
		} else {
			System.out.println("allready exists");
			return true;
		}
	}

	public String logOn(String username, String password) throws NonUniqueResultException {
		try {
			Class<? extends User> kind = Partner.class;
			Partner partner = (Partner) searchUser(kind, username, password);
			if (partner != null) {
				partnerService.setPartner(partner);
				clientService.setClient(null);
				employeeService.setEmployee(null);
				System.out.println("partner session made");
				return "partnerLoggedIn";
			} else {
				kind = Client.class;
				Client client = (Client) searchUser(kind, username, password);
				if (client != null) {
					clientService.setClient(client);
					partnerService.setPartner(null);
					employeeService.setEmployee(null);
					System.out.println("client session made");
					return "clientLoggedIn";
				} else {
					kind = Employee.class;
					Employee employee = (Employee) searchUser(kind, username, password);
					if (employee != null) {
						employeeService.setEmployee(employee);
						partnerService.setPartner(null);
						clientService.setClient(null);
						System.out.println("employee session made");
						return "employeeLoggedIn";
					}
				}
			}
		} catch (NoResultException ex) {
			System.out.println("No user found with username " + username);
		}

		return "failedtoLogIn";
	}

	/*
	 * public void testingFunctionality() { if (session == null) {
	 * System.out.println("user has not logged in"); } else {
	 * session.testFunctionality(); } }
	 */

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

	public List<String> getRegions() {
		return Arrays.asList(GlobalRegion.values().toString());
	}
}
