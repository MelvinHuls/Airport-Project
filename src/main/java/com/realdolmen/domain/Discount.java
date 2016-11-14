package com.realdolmen.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Discount {
	@Id
	@GeneratedValue
	private Long id;

	private Date begin;
	private Date end;
	private Double percentage;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "DISCOUNT_ID")
	private Discounts discounts;

	public Discount() {

	}

	public Discount(Date begin, Date end, Double percentage) {
		super();
		this.begin = begin;
		this.end = end;
		this.percentage = percentage;
	}

	public Discount(Discounts discounts, Date begin, Date end, Double percentage) {
		super();
		this.begin = begin;
		this.end = end;
		this.percentage = percentage;
		this.discounts = discounts;
	}

	public Discount(Discount discount) {
		this.begin = discount.getBegin();
		this.end = discount.getEnd();
		this.percentage = discount.getPercentage();
		this.discounts = discount.getDiscounts();
	}

	public Date getBegin() {
		return begin;
	}

	public void setBegin(Date begin) {
		this.begin = begin;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public Double getPercentage() {
		return percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}

	public Discounts getDiscounts() {
		return discounts;
	}

	public void setDiscounts(Discounts discounts) {
		this.discounts = discounts;
	}

	public Long getId() {
		return id;
	}

}
