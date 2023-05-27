package com.example.mienspa.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.mienspa.model.OrdersSer;
import com.example.mienspa.repository.OrderSeceRepository;

@Service
public class OrderSerService extends ServiceAbstract<OrderSeceRepository, OrdersSer, String>{
	public Integer getCountOrSerByDate(LocalDate date) {
		return repository.countOrSerByDate(date);
	}

	public List<OrdersSer> getAllByUserId(String id) {
		return repository.findAllByUserId(id);
	}
	
	public List<OrdersSer> getAllByUpdateDate(String date) {
		return repository.findAllByUpdateDay(date+"%");
	}
}
