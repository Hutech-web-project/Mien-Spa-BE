package com.example.mienspa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.mienspa.model.RefreshToken;


public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
	@Query(value = "SELECT * FROM refreshtoken WHERE JwtId  = ?", nativeQuery = true)
	RefreshToken findByRefreshToken(String JwtId);
}
