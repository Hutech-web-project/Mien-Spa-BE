package com.example.mienspa.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.mienspa.model.OrderSerDetail;
import com.example.mienspa.repository.OrderSerceDetailRepository;

@Service
public class OrderSerDetailService extends ServiceAbstract<OrderSerceDetailRepository, OrderSerDetail, Integer>{
	public List<OrderSerDetail> getAllByOrSerId(String id) {
		return repository.findAllByOrSerId(id);
	}
}
