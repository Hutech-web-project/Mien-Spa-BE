package com.example.mienspa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.mienspa.model.Category;


public interface CategoryRepository extends JpaRepository<Category, Integer>{
	@Query(value = "SELECT * FROM category WHERE cate_name = ? and cate_id_parent = ?", nativeQuery = true)
	Category getCateByNameAndPrId(String cateName, Integer prId);
	@Query(value = "SELECT * FROM `category` WHERE cate_id_parent = ?", nativeQuery = true)
	Category getCateByPrId(Integer prId);	
}
