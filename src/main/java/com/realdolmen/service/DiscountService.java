package com.realdolmen.service;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.realdolmen.Exceptions.CompanyCanOnlyHaveOneDiscountListException;
import com.realdolmen.beans.DiscountBean;
import com.realdolmen.domain.Discount;
import com.realdolmen.domain.Discounts;
import com.realdolmen.domain.Flight;

@ViewScoped
@ManagedBean(name="discountService")
public class DiscountService {
	@EJB
	private DiscountBean dbean;
	
	private Discounts discounts;
	
	private String company;
	
	private Discount discount;
	
	@PostConstruct
	public void init() throws CompanyCanOnlyHaveOneDiscountListException {
		System.out.println("init");
		discounts = dbean.getDiscounts(company);
		discount = new Discount();
		discount.setDiscounts(discounts);
	}
	
	public Flight linkDiscounts(Flight flight) {
		return dbean.linkDiscounts(flight); 
	}
	
	public Discounts getDiscounts() throws CompanyCanOnlyHaveOneDiscountListException {
		return dbean.getDiscounts(company);
	}
	
	public List<Discount> getAllDiscounts() {
		return dbean.getAllDiscounts(company);
	}
	
	public void deleteDiscount(Discount discount) {
		dbean.deleteDiscount(discount);
	}
	
	public void updateDiscounts() {
		dbean.update(discounts);
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public Discount getDiscount() {
		return discount;
	}

	public void setDiscount(Discount discount) {
		this.discount = discount;
	}

	public void setDiscounts(Discounts discounts) {
		this.discounts = discounts;
	}
	
	
	public void addDiscount() {
		System.out.println("add discount");
		dbean.add(discount);
		System.out.println("discount persisted");
		discount.setPercentage(null);
		discount.setBegin(null);
		discount.setEnd(null);
		System.out.println("discount whiped");
	}
	
	public void test() {
		System.out.println("test succeeded");
	}
	

}
