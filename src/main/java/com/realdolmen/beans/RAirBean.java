package com.realdolmen.beans;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
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
@Stateless
@Remote
@ManagedBean(name = "rAirBean")
public class RAirBean {
	@PersistenceContext
	private EntityManager em;

	@EJB
	private PartnerService partnerService;

	@EJB
	private ClientService clientService;

	@EJB
	private EmployeeService employeeService;

	private User user;

	private User loggedIn;

	@PostConstruct
	public void setup() {
		user = new User();
	}

	public String registerClient() {
		if (!existingUser(user.getEmail())) {
			clientService.create(new Client(user.getUsername(), user.getPassword(), user.getEmail()));
			return "index";
		} else {
			return "register";
		}
	}

	public String registerEmployee() {
		if (!existingUser(user.getEmail())) {
			employeeService.create(new Employee(user.getEmail(), user.getPassword(), user.getEmail()));
			return "Success";
		} else {
			return "Failure";
		}
	}

	public String registerPartner() {
		if (!existingUser(user.getEmail())) {
			Partner client = new Partner(user.getEmail(), user.getPassword(), user.getEmail());
			partnerService.create(client);
			return "Success";
		} else {
			return "Failure";
		}
	}

	private boolean existingUser(String email) throws NonUniqueResultException {
		Partner p = partnerService.findByEmail(email);
		Employee e = employeeService.findByEmail(email);
		Client c = clientService.findByEmail(email);
		if (p == null && e == null && c == null) {
			System.out.println("does not exist");
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
				loggedIn.setEmail(partner.getEmail());
				loggedIn.setPassword(partner.getPassword());
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void validatePassword(ComponentSystemEvent event) {

		FacesContext fc = FacesContext.getCurrentInstance();

		UIComponent components = event.getComponent();

		// get password
		UIInput uiInputPassword = (UIInput) components.findComponent("inputPassword");
		String password = uiInputPassword.getLocalValue() == null ? "" : uiInputPassword.getLocalValue().toString();
		String passwordId = uiInputPassword.getClientId();

		// get confirm password
		UIInput uiInputConfirmPassword = (UIInput) components.findComponent("inputPassword2");
		String confirmPassword = uiInputConfirmPassword.getLocalValue() == null ? ""
				: uiInputConfirmPassword.getLocalValue().toString();

		// Let required="true" do its job.
		if (password.isEmpty() || confirmPassword.isEmpty()) {
			return;
		}

		if (!password.equals(confirmPassword)) {

			FacesMessage msg = new FacesMessage("Password must match confirm password");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			fc.addMessage(passwordId, msg);
			fc.renderResponse();

		}

	}

	public User getLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(User loggedIn) {
		this.loggedIn = loggedIn;
	}
}
