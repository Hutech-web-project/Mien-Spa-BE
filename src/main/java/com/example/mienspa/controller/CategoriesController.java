package com.example.mienspa.controller;

import java.time.Instant;
import java.util.Date;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mienspa.dto.CategoryDTO;
import com.example.mienspa.model.Category;
import com.example.mienspa.service.CategoryService;
import com.example.mienspa.service.ProductService;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class CategoriesController {
	
	@Autowired
	CategoryService service;
	
	@Autowired
	ProductService servicePro;
	
	@Autowired
	private ModelMapper modelMapper;
	
	HttpHeaders responseHeaders = new HttpHeaders();
	Date date = Date.from(Instant.now());
	@GetMapping("/Category")
	public ResponseEntity<?> getAll(){		
		try {
			return new ResponseEntity<>(
					service.getAll()
					.stream()
					.map(post -> modelMapper.map(post, CategoryDTO.class))
					.collect(Collectors.toList()), 
					responseHeaders,
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Connect server fail", responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/Category/{id}")
	public ResponseEntity<?> getById(@PathVariable("id") Integer id){		
		try {
			Category entity = service.getById(id);
			CategoryDTO dtoResponse = modelMapper.map(entity,CategoryDTO.class);
			return new ResponseEntity<>(dtoResponse, responseHeaders, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Connect server fail", responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	@PostMapping("/Category")
//	@PreAuthorize("hasRole('MODERATOR') and hasRole('CATEGORY') or hasRole('ADMIN')")
	public ResponseEntity<?> create_category(@RequestBody CategoryDTO dto){
		try {
			Category entityRequest = modelMapper.map(dto, Category.class);
			if(entityRequest.getCateName() != null) {
				Category checkCate = service.getByNameAndParentsId(entityRequest.getCateName(), entityRequest.getCateIdParent());
				if(checkCate == null) {
					if(entityRequest.getCateIdParent() == null) {
						entityRequest.setCateIdParent(0);
					}
					entityRequest.setCreatedAt(date);
					entityRequest.setIsDelete(false);
					service.create(entityRequest);
					return new ResponseEntity<>("Success",responseHeaders,HttpStatus.CREATED);
				}else {
					return new ResponseEntity<>("Category already exists",responseHeaders,HttpStatus.NOT_FOUND);
				}
				
			}else {
				return new ResponseEntity<>("Fail",responseHeaders,HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			return new ResponseEntity<>("Connect server fail",responseHeaders,HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	@PutMapping("/Category")
//	@PreAuthorize("hasRole('MODERATOR') and hasRole('CATEGORY') or hasRole('ADMIN')")
	public ResponseEntity<?> update_category(@RequestBody CategoryDTO dto){
		try {
			Category entityRequest = modelMapper.map(dto, Category.class);
			if(entityRequest.getCateName() != null
			&& entityRequest.getCateId() != null
			&& entityRequest.getCateIdParent() != null) {
				Category checkCateID = service.getById(entityRequest.getCateId());
				Category checkCateName = service.getByNameAndParentsId(entityRequest.getCateName(), entityRequest.getCateIdParent());
				if(checkCateID != null) {
					if((checkCateName != null && checkCateID.getCateName().equals(entityRequest.getCateName())) || checkCateName == null) {
						entityRequest.setUpdatedAt(date);
						service.create(entityRequest);
						return new ResponseEntity<>("Success",responseHeaders,HttpStatus.CREATED);
					}else {
						return new ResponseEntity<>("Category name can't duplicate",responseHeaders,HttpStatus.NOT_FOUND);
					}
				}else {
					return new ResponseEntity<>("Category doesn't exists yet",responseHeaders,HttpStatus.NOT_FOUND);
				}
				
			}else {
				return new ResponseEntity<>("Fail",responseHeaders,HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			return new ResponseEntity<>("Connect server fail",responseHeaders,HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	@DeleteMapping(value = "/Category/{id}")
//	@PreAuthorize("hasRole('MODERATOR') and hasRole('CATEGORY') or hasRole('ADMIN')")
	public ResponseEntity<?> deleteCategory(@PathVariable("id") Integer id) {	
		try {
			Category entity = service.getById(id);
			if (service.getById(id) != null ) {
				if(entity.getProducts().size() == 0 && entity.getCateIdParent()==0 && service.getByParentsId(id) == null) {
					service.delete(entity);
					return new ResponseEntity<>("Success", responseHeaders, HttpStatus.OK);
				}else if(entity.getProducts().size() == 0 && entity.getCateIdParent()!=0){
					service.delete(entity);
					return new ResponseEntity<>("Success", responseHeaders, HttpStatus.OK);
				}else if(servicePro.getProByCateIdProOn(id) != null){
					entity.setIsDelete(true);
					service.create(entity);
					return new ResponseEntity<>("Success", responseHeaders, HttpStatus.OK);
				}			
				return new ResponseEntity<>("Fail", responseHeaders, HttpStatus.NOT_FOUND);
			}else {
				return  new ResponseEntity<>("Fail", responseHeaders, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Connect server fail",responseHeaders,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
