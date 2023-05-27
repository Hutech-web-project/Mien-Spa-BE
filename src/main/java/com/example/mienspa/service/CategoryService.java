package com.example.mienspa.service;

import org.springframework.stereotype.Service;

import com.example.mienspa.model.Category;
import com.example.mienspa.repository.CategoryRepository;


@Service
public class CategoryService extends ServiceAbstract<CategoryRepository, Category, Integer> {
	
	public Category getByNameAndParentsId(String cateName, Integer parId) {
		return repository.getCateByNameAndPrId(cateName, parId);
	}
	
	public Category getByParentsId(Integer parId) {
		return repository.getCateByPrId(parId);
	}
}
