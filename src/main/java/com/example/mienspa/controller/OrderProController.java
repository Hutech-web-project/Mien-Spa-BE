package com.example.mienspa.controller;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

import com.example.mienspa.dto.OrderProDTO;
import com.example.mienspa.dto.ProductDetailsDTO;
import com.example.mienspa.model.OrdersPro;
import com.example.mienspa.model.OrdersProDetail;
import com.example.mienspa.model.Product;
import com.example.mienspa.service.OrderProDetailService;
import com.example.mienspa.service.OrderProService;
import com.example.mienspa.service.ProductService;
import com.example.mienspa.service.UserService;



@CrossOrigin
@RestController
@RequestMapping("/api")
public class OrderProController {
	
	@Autowired
	private OrderProService service;
	
	@Autowired
	private OrderProDetailService DeProSer;
	
	@Autowired
	private ProductService proSer;
	
	@Autowired
	private UserService UseSer;

	@Autowired
	private ModelMapper modelMapper;

	HttpHeaders responseHeaders = new HttpHeaders();
	Date date = Date.from(Instant.now());
	@GetMapping(value = "/OrdersPro")
	@PreAuthorize("hasRole('USER') or hasRole('ORDER_PRODUCT') or hasRole('ADMIN')")
	public ResponseEntity<?> getAll(){
		try {
			List<OrdersPro> entityList = service.getAll();
			List<OrderProDTO> dtos = entityList.stream().map(user -> modelMapper.map(user, OrderProDTO.class))
					.collect(Collectors.toList());
			for (OrdersPro entity : entityList) {
				for (OrderProDTO dto : dtos) {
					if (dto.getOrProId().equals(entity.getOrProId())) {
						if(entity.getUsers() != null) {
							dto.setOrProUserId(entity.getUsers().getUsId());

						}else {
							dto.setOrProUserId(null);

						}
					}
				}
			}
			return new ResponseEntity<>(dtos, responseHeaders, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Connect server fail", responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value = "/OrdersPro/User/{userId}")
	@PreAuthorize("hasRole('USER') or hasRole('ORDER_PRODUCT') or hasRole('ADMIN')")
	public ResponseEntity<?> getAllByUserId(@PathVariable("userId") String userId){
		try {
			List<OrdersPro> entityList = service.getAllByUserId(userId);
			List<OrderProDTO> dtos = entityList.stream().map(user -> modelMapper.map(user, OrderProDTO.class))
					.collect(Collectors.toList());
			for (OrdersPro entity : entityList) {
				List<ProductDetailsDTO> listDetails = new ArrayList<>();
				for (OrderProDTO dto : dtos) {
					if (dto.getOrProId().equals(entity.getOrProId())) {
						if(entity.getUsers() != null) {
							dto.setOrProUserId(entity.getUsers().getUsId());

						}else {
							dto.setOrProUserId(null);

						}
						for (OrdersProDetail order : entity.getOrdersprodetails()) {
							ProductDetailsDTO detail = new  ProductDetailsDTO(order.getOrdProProductName(),order.getOrdProProductPrice(),order.getOrdProQuantity(),order.getProduct().getProId());
							listDetails.add(detail);
						}
						dto.setOrProTotal(entity.getOrProTotal());
						dto.setListProId(listDetails);	
					}
				}
			}
			return new ResponseEntity<>(dtos, responseHeaders, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Connect server fail", responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value = "/OrdersPro/UpdateDate/{date}")
	@PreAuthorize("hasRole('USER') or hasRole('ORDER_PRODUCT') or hasRole('ADMIN')")
	public ResponseEntity<?> getAllByUpdateDate(@PathVariable("date") String date){
		try {
			List<OrdersPro> entityList = service.getAllByUpdateDate(date);
			List<OrderProDTO> dtos = entityList.stream().map(user -> modelMapper.map(user, OrderProDTO.class))
					.collect(Collectors.toList());

			for (OrdersPro entity : entityList) {
				for (OrderProDTO dto : dtos) {
					if (dto.getOrProId().equals(entity.getOrProId())) {
						if(entity.getUsers() != null) {
							dto.setOrProUserId(entity.getUsers().getUsId());

						}else {
							dto.setOrProUserId(null);
						}
					}
				}
			}
			return new ResponseEntity<>(dtos, responseHeaders, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Connect server fail", responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value = "/OrdersPro/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ORDER_PRODUCT') or hasRole('ADMIN')")
	public ResponseEntity<?> getById(@PathVariable("id") String id){
		try {
			OrdersPro entity = service.getById(id);
			if (service.getById(id) != null) {
				OrderProDTO dto = modelMapper.map(entity, OrderProDTO.class);
				if(entity.getUsers() != null) {
					dto.setOrProUserId(entity.getUsers().getUsId());
				}else {
					dto.setOrProUserId(null);
				}
				return new ResponseEntity<>(dto, responseHeaders, HttpStatus.OK);
			}
			return new ResponseEntity<>("This product order does not exist", responseHeaders, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<>("Connect server fail", responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PostMapping(value = "/OrdersPro")
	public ResponseEntity<?> create(@RequestBody OrderProDTO dto) {
		try {
			OrdersPro entityRequest = modelMapper.map(dto, OrdersPro.class);
			entityRequest.setOrProId(idOrProIentity());
			if(dto.getOrProAddress() != null 
			   && dto.getOrProUserName() != null
			   && dto.getOrProAddress() != null
			   && dto.getOrProPhoneNo() != null
			   && dto.getOrProStatus() != null
			   && dto.getOrProPayment() != null
			   && dto.getOrProTotal() != null
			) {
				entityRequest.setCreatedAt(date);
				if(dto.getOrProUserId() != null) {
					entityRequest.setUsers(UseSer.getById(dto.getOrProUserId()));
				}
				OrdersPro entity = service.create(entityRequest);
				for (ProductDetailsDTO item : dto.getListProId()) {
					if(!item.getProProductName().isEmpty() && item.getProQuantity()!= 0 && item.getProProductPrice() != 0) {
						if(proSer.getById(item.getProductId())!= null) {
							Product product = proSer.getById(item.getProductId());
							OrdersProDetail OrProDeEntity = new OrdersProDetail(entity,product,item.getProProductName(),item.getProProductPrice(),item.getProQuantity());
							DeProSer.create(OrProDeEntity);
						}
					}
				}			
				return new ResponseEntity<>("Success", responseHeaders, HttpStatus.CREATED);
			}else {
				return new ResponseEntity<>("Fail",responseHeaders,HttpStatus.ACCEPTED);
			}	
		} catch (Exception e) {
			return new ResponseEntity<>("Connect server fail", responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping(value = "/OrdersPro/{id}")
	@PreAuthorize("hasRole('MODERATOR') and hasRole('ORDER_PRODUCT') or hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<?> update(@PathVariable("id") String id, @RequestBody OrderProDTO dto) {
		try {
			if (service.getById(id) != null) {
				if(dto.getOrProAddress() != null 
						   && dto.getOrProUserName() != null
						   && dto.getOrProAddress() != null
						   && dto.getOrProPhoneNo() != null
						   && dto.getOrProStatus() != null
						   && dto.getOrProPayment() != null
						   && dto.getOrProTotal() != null
						   && dto.getCreatedAt() != null
				) {
					OrdersPro entityRequest = modelMapper.map(dto, OrdersPro.class);
					entityRequest.setUpdatedAt(date);
					if(entityRequest.getOrProUserName().equals("") == false) {
						entityRequest.setUsers(UseSer.getById(dto.getOrProUserId()));
						entityRequest.setOrProId(id);
						service.create(entityRequest);
						return new ResponseEntity<>("Success", responseHeaders, HttpStatus.OK);
					}else {
						entityRequest.setOrProId(id);
						service.create(entityRequest);
						return new ResponseEntity<>("Success", responseHeaders, HttpStatus.OK);
					}
				}else {
					return new ResponseEntity<>("Fail",responseHeaders,HttpStatus.ACCEPTED);
				}
				
			}
			return  new ResponseEntity<>("This product order does not exist", responseHeaders, HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<>("Connect server fail", responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping(value = "/OrdersProArray")
	@PreAuthorize("hasRole('MODERATOR') and hasRole('ORDER_PRODUCT') or hasRole('ADMIN')")
	public ResponseEntity<?> deleteArrayOrdersPro(@RequestBody String[] id) {
		try {
			for (String orProId : id) {		
				if(service.getById(orProId) != null) {
					OrdersPro entity = service.getById(orProId);
					for (OrdersProDetail item : entity.getOrdersprodetails()) {
						DeProSer.delete(item);
					}
					service.delete(entity);
				}
			}		
			return new ResponseEntity<>("Success", responseHeaders, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Connect server fail", responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping(value = "/OrdersPro")
	@PreAuthorize("hasRole('MODERATOR') and hasRole('ORDER_PRODUCT') or hasRole('ADMIN')")
	public ResponseEntity<?> deleteOrdersPro(@RequestBody String id) {
		try {
			OrdersPro entity = service.getById(id);
			if (service.getById(id) != null) {
				for (OrdersProDetail item : entity.getOrdersprodetails()) {
					DeProSer.delete(item);
				}
				service.delete(entity);			
				return new ResponseEntity<>("Success", responseHeaders, HttpStatus.OK);
			}else {
				return  new ResponseEntity<>("This product order does not exist", responseHeaders, HttpStatus.ACCEPTED);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Connect server fail", responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public String idOrProIentity() {
		LocalDate today = LocalDate.now();
		int numberUser = service.getCountOrProByDate(today);
		int year = (today.getYear() % 100);
		String number = String.valueOf(numberUser);
		String id = null;
		switch (number.length()) {
		case 1:
			if (numberUser != 9) {
				id = "ORPRO" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "000" + (numberUser + 1);
			} else {
				id = "ORPRO" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "00" + (numberUser + 1);
			}
			break;
		case 2:
			if (numberUser != 99) {
				id = "ORPRO" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "00" + (numberUser + 1);
			} else {
				id = "ORPRO" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "0" + (numberUser + 1);
			}
			break;
		case 3:
			if (numberUser != 999) {
				id = "ORPRO" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "0" + (numberUser + 1);
			} else {
				id = "ORPRO" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "" + (numberUser + 1);
			}
			break;
		case 4:
			id = "ORPRO" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "" + (numberUser + 1);
			break;
		default:
			System.out.print("Id error");
			break;
		}
		return id;
	}
}
