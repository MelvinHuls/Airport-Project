package com.realdolmen.service;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

import com.realdolmen.beans.UserBeanRemote;
import com.realdolmen.domain.User;
import com.realdolmen.repository.UserRepository;

@Stateless
@LocalBean
public class UserService implements UserBeanRemote {

	@Inject
	private UserRepository userRepository;

	public String register(User user) {
		User checkUser = findByEmail(user.getEmail());

		if (checkUser != null) {
			System.out.println("old user: " + user.getEmail());
			return userRepository.update(user);
		} else {
			System.out.println("new user: " + user.getUsername());
			return userRepository.create(user);
		}

	}

	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

}