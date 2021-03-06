package com.realdolmen.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.realdolmen.domain.Employee;
import com.realdolmen.domain.Flight;
import com.realdolmen.domain.Location;
import com.realdolmen.repository.EmployeeRepository;
import com.realdolmen.repository.LocationRepository;

//@EJB(name="java:global/RAir/EmployeeService", beanInterface = SessionRemote.class, beanName="EmployeeService")
@Remote
@Stateful
@LocalBean
public class EmployeeService implements SessionRemote, AbstractService<Employee> {
	private Employee employee;

	private Location location;
	private Flight flight;

	@RequestScoped
	private LocationRepository lRepo;

	@PersistenceContext
	EntityManager em;

	@EJB
	private EmployeeRepository eRepo;

	@Override
	public void create(Employee employee) {
		eRepo.create(employee);
	}

	@Override
	public Employee findById(Long id) {
		return eRepo.read(id);
	}

	@Override
	public String update(Employee employee) {
		return eRepo.update(employee);
	}

	@Override
	public void delete(Employee employee) {
		eRepo.delete(employee);
	}

	@Override
	public List<Employee> findAll() {
		return eRepo.findAll();
	}

	public EmployeeService() {
		super();
	}

	public EmployeeService(Employee employee) {
		super();
		this.employee = employee;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public List<Location> obtainLocations() {
		return lRepo.findAll();
	}

	public String storeLocation(Location location) {
		this.location = location;
		return "edit";
	}

	public String changeLocation() {
		// this.location.setRegion();

		em.merge(this.location);
		this.location = null;
		return "success";
	}

	public String newLocation() {
		this.location = new Location();
		return "add";
	}

	public String addLocation() {
		// this.location.setRegion();

		em.persist(this.location);
		this.location = null;

		return "success";
	}

	public String deleteLocation() {
		em.remove(em.find(Location.class, location.getId()));
		return "deleted";
	}

	public Flight getFlight() {
		return flight;
	}

	public void setFlight(Flight flight) {
		this.flight = flight;
	}

	public String editFlight(Flight flight) {
		this.flight = flight;
		return "edit";
	}

	public Employee findByEmail(String email) {
		return eRepo.findByEmail(email);
	}
}
