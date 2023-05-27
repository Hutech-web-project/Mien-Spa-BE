package com.example.mienspa.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.example.mienspa.model.UserRole;
import com.example.mienspa.repository.UserRoleRepository;


@Service
public class UserRoleService extends ServiceAbstract<UserRoleRepository,UserRole, Integer>{

	public List<UserRole> getAllByUserId(String id) {
		return repository.findAllByUserId(id);
	}

}
