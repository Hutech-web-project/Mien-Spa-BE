package com.example.mienspa.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.example.mienspa.model.Serce;
import com.example.mienspa.repository.SerceRepository;

@Service
public class SerceService extends ServiceAbstract<SerceRepository, Serce, String>{

	public String getIdLast() {
		return repository.getLastIdSerce();
	}

	public Integer getCountSerByDate(LocalDate date) {
		return repository.countSerByDate(date);
	}
}
