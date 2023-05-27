package com.example.mienspa.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mienspa.model.Users;
import com.example.mienspa.repository.UserRepository;


@Service
public class UserService extends ServiceAbstract<UserRepository, Users, String> {
	@Autowired
	private UserRepository repository;
	
	public Boolean checkMail(String email) {
		if(repository.getUserByEmail(email) != null) {
			return true;
	}
		return false;
	}
	
	public Integer getCountUserByDate(LocalDate date) {
		return repository.countUserByDate(date);
	}
	public Users getByEmail(String email) {
		return repository.getUserByEmail(email);
	}
	
}
