package com.realdolmen.service;

import com.realdolmen.domain.Partner;

public class PartnerService implements SessionRemote{
	
	private Partner partner;
	
	
	
	public PartnerService() {
		super();
	}

	public PartnerService(Partner partner) {
		super();
		this.partner = partner;
	}

	public void testFunctionality() {
		partner = new Partner();
		partner.changeDepartureTime();
	}

	public Partner getPartner() {
		return partner;
	}

	public void setPartner(Partner partner) {
		this.partner = partner;
	}	
	
	
}
