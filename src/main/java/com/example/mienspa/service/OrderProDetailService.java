package com.example.mienspa.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.mienspa.model.OrdersProDetail;
import com.example.mienspa.repository.OrderProDetailRepository;

@Service
public class OrderProDetailService extends ServiceAbstract<OrderProDetailRepository, OrdersProDetail, String> {
	public List<OrdersProDetail> getAllByOrProId(String id) {
		return repository.findAllByOrProId(id);
	}
}
