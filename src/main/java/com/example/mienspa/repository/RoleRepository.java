package com.example.mienspa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.mienspa.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{
	@Query(value = "SELECT * FROM role WHERE ro_Name = ?", nativeQuery = true)
	Role getByRoleName(String role);
}
