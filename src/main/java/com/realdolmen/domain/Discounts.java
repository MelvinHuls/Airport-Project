package com.realdolmen.domain;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Discounts {	
	@Id
	@GeneratedValue
	private Long id;
	
	@OneToMany(mappedBy="discounts")
	private List<Discount> discounts;
	private Double weekendDiscount;	
	//night is between 23:00 and 06:00
	private Double nightlyDiscount; 
	private static int BEGIN_NIGHT = 23;
	private static int END_NIGHT = 6;
	
	@ElementCollection
	private Map<Integer, Double> seatsDiscount;
	
	private String company;

	public Discounts() {
		discounts = new ArrayList<Discount>();
		weekendDiscount = 0d;
		nightlyDiscount = 0d;
		seatsDiscount = new HashMap<Integer,Double>();
	}
	
	public void addDiscount(Discount discount) {
		if(discount.getDiscounts() != this){
			discount.setDiscounts(this);
		}
		discounts.add(discount);
	}

	public List<Discount> getDiscounts() {
		return discounts;
	}

	public void setDiscounts(List<Discount> discounts) {
		this.discounts = discounts;
	}

	public Double getWeekendDiscount() {
		return weekendDiscount;
	}

	public Double getNightlyDiscount() {
		return nightlyDiscount;
	}

	public void setNightlyDiscount(Double nightlyDiscount) {
		this.nightlyDiscount = nightlyDiscount;
	}

	public void setWeekendDiscount(Double weekendDiscount) {
		this.weekendDiscount = weekendDiscount;
	}	
	
	public Double calculatePrice(Double price, Date date, int seats) {	
		System.out.println("beginning calculate price");
		Double discountedPrice = price;
		 Calendar c = Calendar.getInstance();
		c.set(date.getYear(), date.getMonth(), date.getDay());
		if(c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
				discountedPrice *= (1-weekendDiscount);
		}
		System.out.println("after week discount");
		
		if(date.getHours() >= BEGIN_NIGHT || date.getHours() < END_NIGHT) {
				discountedPrice *= (1-nightlyDiscount);
		}
		System.out.println("after night discount");
		
		Iterator<Discount> iterator = discounts.iterator();
		while(iterator.hasNext()) {
			Discount discount = iterator.next();
			if(discount.getBegin().getTime() < date.getTime() && discount.getEnd().getTime() > date.getTime()) {
					discountedPrice *= (1-discount.getPercentage());
			}
		}
		
		System.out.println("after discounts iteration");
		
		Set<Integer> keySet = this.seatsDiscount.keySet();
		Iterator<Integer> iterator2 = keySet.iterator();
		Integer largestAmountOfSeats = 0;
		while(iterator2.hasNext() && seats > largestAmountOfSeats) {
			Integer amountOfSeats = iterator2.next();
			if(seats > amountOfSeats && amountOfSeats > largestAmountOfSeats) {
				largestAmountOfSeats = amountOfSeats;
			}
		}
		System.out.println("after seats discount iteration");
		
		if(largestAmountOfSeats > 0) {
			discountedPrice *= (1-this.seatsDiscount.get(largestAmountOfSeats));
		}
		
		System.out.println("returning " + discountedPrice);
		
		return discountedPrice;
	}

	public Map<Integer, Double> getSeatsDiscount() {
		return seatsDiscount;
	}

	public void setSeatsDiscount(Map<Integer, Double> seatsDiscount) {
		this.seatsDiscount = seatsDiscount;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public Long getId() {
		return id;
	}	
	
	public void addSeatsDiscount(Integer amountOfSeats, Double percentage) {
		this.seatsDiscount.put(amountOfSeats, percentage);
	}
	
	
}
