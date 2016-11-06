package com.realdolmen.service;

import java.util.List;

import javax.ejb.Remote;

import com.realdolmen.Exceptions.AccessRightsException;
import com.realdolmen.domain.Flight;

@Remote
public interface SessionRemote {
	public default void testFunctionality() {
		System.out.println("error");
	}

	public default List<Flight> obtainFlights() throws AccessRightsException {
		throw new AccessRightsException("Not enough access rights to edit flights");
	}

	public default Flight getFlight(Long id) throws AccessRightsException {
		throw new AccessRightsException("Not enough access rights to request data flight");
	}

	public default String changeFlight() throws AccessRightsException {
		throw new AccessRightsException("Not enough access rights to edit the data of a flight");
	}
}
