package com.realdolmen.service;

import java.util.List;

import com.realdolmen.domain.Partner;

public interface AbstractService<T> {
	List<T> findAll();

	void create(T param);

	T findById(Long id);

	String update(T param);

	void delete(T param);

}
