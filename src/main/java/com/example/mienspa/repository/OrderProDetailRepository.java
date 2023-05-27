package com.example.mienspa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.mienspa.model.OrdersProDetail;



public interface OrderProDetailRepository extends JpaRepository<OrdersProDetail, String> {
	@Query(value = "SELECT * FROM ordersprodetail WHERE ordPro_OrderId = ?", nativeQuery = true)
	List<OrdersProDetail> findAllByOrProId(String id);
}
