package com.example.mienspa.service;

import org.springframework.stereotype.Service;
import com.example.mienspa.model.Role;
import com.example.mienspa.repository.RoleRepository;

@Service
public class RoleService extends ServiceAbstract<RoleRepository, Role, Integer>{	
	public Role getByName(String role) {
		return repository.getByRoleName(role);
	}

}
