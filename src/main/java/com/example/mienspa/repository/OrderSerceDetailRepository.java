package com.example.mienspa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.mienspa.model.OrderSerDetail;

public interface OrderSerceDetailRepository extends JpaRepository<OrderSerDetail, Integer> {
	@Query(value = "SELECT * FROM orderserdetail WHERE ordSer_OrderId = ?", nativeQuery = true)
	List<OrderSerDetail> findAllByOrSerId(String id);
}
