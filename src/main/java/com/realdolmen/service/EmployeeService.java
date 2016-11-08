package com.realdolmen.service;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.realdolmen.domain.Employee;
import com.realdolmen.domain.Location;
import com.realdolmen.repository.EmployeeRepository;

//@EJB(name="java:global/RAir/EmployeeService", beanInterface = SessionRemote.class, beanName="EmployeeService")
@Stateful
@LocalBean
public class EmployeeService implements SessionRemote {
	private Employee employee;

	private Location location;

	@PersistenceContext
	EntityManager em;

	private EmployeeRepository eRepo;

	public void create(Employee employee) {
		eRepo.create(employee);
	}

	public Employee read(Long id) {
		return eRepo.read(id);
	}

	public void update(Employee employee) {
		eRepo.update(employee);
	}

	public void delete(Employee employee) {
		eRepo.delete(employee);
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
		return em.createQuery("select l from Location l", Location.class).getResultList();
	}

	public String storeLocation(Location location) {
		this.location = location;
		return "edit";
	}

	public String changeLocation() {
		//this.location.setRegion();

		em.merge(this.location);
		this.location = null;
		return "success";
	}

	public String newLocation() {
		this.location = new Location();
		return "add";
	}

	public String addLocation() {
		//this.location.setRegion();
	
		em.persist(this.location);
		this.location = null;

		return "success";
	}

	public String deleteLocation() {
		em.remove(em.find(Location.class, location.getId()));
		return "deleted";
	}
}
