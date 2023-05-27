package com.example.mienspa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
 abstract class ServiceAbstract<R extends JpaRepository<T, ID>,T,ID> {
	@Autowired
	R repository;
	public T create(T t) {
		return repository.save(t);
	}
	
	public List<T> getAll() {
		return repository.findAll();
	}

	public T getById(ID id) {
		return repository.findById(id).orElse(null);
	}

	public void delete(T t) {
		repository.delete(t);
	}
}
