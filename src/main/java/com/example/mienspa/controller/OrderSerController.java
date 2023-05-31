package com.example.mienspa.controller;

import java.time.LocalDate;
import java.util.ArrayList;
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

import com.example.mienspa.dto.OrdersSerDTO;
import com.example.mienspa.dto.ServiceDetailsDTO;
import com.example.mienspa.model.OrderSerDetail;
import com.example.mienspa.model.OrdersSer;
import com.example.mienspa.model.Serce;
import com.example.mienspa.service.OrderSerDetailService;
import com.example.mienspa.service.OrderSerService;
import com.example.mienspa.service.SerceService;
import com.example.mienspa.service.UserService;


@CrossOrigin
@RestController
@RequestMapping("/api")
public class OrderSerController {

	@Autowired
	private OrderSerService service;

	@Autowired
	private OrderSerDetailService OrSerDeSer;
	
	@Autowired
	private SerceService seceSer;

	@Autowired
	private UserService UseSer;

	@Autowired
	private ModelMapper modelMapper;

	HttpHeaders responseHeaders = new HttpHeaders();

	@GetMapping(value = "/OrdersSer")
	@PreAuthorize("hasRole('USER') or hasRole('ORDER_SERVICE') or hasRole('ADMIN')")
	public ResponseEntity<?> getAll() {
		try {
			List<OrdersSer> entityList = service.getAll();
			List<OrdersSerDTO> dtos = entityList.stream().map(user -> modelMapper.map(user, OrdersSerDTO.class))
					.collect(Collectors.toList());

			for (OrdersSer entity : entityList) {
				List<ServiceDetailsDTO> listDetails = new ArrayList<>();
				for (OrdersSerDTO dto : dtos) {
					if (dto.getOrSerId().equals(entity.getOrSerId())) {
						if (entity.getUsers() != null) {
							dto.setOrSerUserId(entity.getUsers().getUsId());			
						} else {
							dto.setOrSerUserId(null);
						}
						for (OrderSerDetail order : entity.getOrderserdetails()) {
							ServiceDetailsDTO serce = new ServiceDetailsDTO(order.getOrdSerServiceName(),order.getOrdSerServicePrice(),order.getSerce().getSeId());
							listDetails.add(serce);
						}
						dto.setOrSer_Total(entity.getOrSerTotal());
						dto.setlistSer(listDetails);
					}
				}
			}
			return new ResponseEntity<>(dtos, responseHeaders, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Connect server fail", responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/OrdersSer/User/{userId}")
	@PreAuthorize("hasRole('USER') or hasRole('ORDER_SERVICE') or hasRole('ADMIN')")
	public ResponseEntity<?> getAllByUserId(@PathVariable("userId") String userId) {
		try {
			List<OrdersSer> entityList = service.getAllByUserId(userId);
			List<OrdersSerDTO> dtos = entityList.stream().map(user -> modelMapper.map(user, OrdersSerDTO.class))
					.collect(Collectors.toList());

			for (OrdersSer entity : entityList) {
				List<ServiceDetailsDTO> listDetails = new ArrayList<>();
				for (OrdersSerDTO dto : dtos) {
					if (dto.getOrSerId().equals(entity.getOrSerId())) {
						if(entity.getUsers() != null) {
							dto.setOrSerUserId(entity.getUsers().getUsId());
						}else {
							dto.setOrSerUserId(null);
						}				
						for (OrderSerDetail order : entity.getOrderserdetails()) {
							ServiceDetailsDTO serce = new ServiceDetailsDTO(order.getOrdSerServiceName(),order.getOrdSerServicePrice(),order.getSerce().getSeId());
							listDetails.add(serce);
						}
						dto.setOrSer_Total(entity.getOrSerTotal());
						dto.setlistSer(listDetails);			
					}
				}
			}
			return new ResponseEntity<>(dtos, responseHeaders, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Connect server fail", responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value = "/OrdersSer/UpdateDate/{date}")
	@PreAuthorize("hasRole('USER') or hasRole('ORDER_SERVICE') or hasRole('ADMIN')")
	public ResponseEntity<?> getAllByUpdateDate(@PathVariable("date") String date) {
		try {
			List<OrdersSer> entityList = service.getAllByUpdateDate(date);
			List<OrdersSerDTO> dtos = entityList.stream().map(user -> modelMapper.map(user, OrdersSerDTO.class))
					.collect(Collectors.toList());

			for (OrdersSer entity : entityList) {
				List<ServiceDetailsDTO> listDetails = new ArrayList<>();
				for (OrdersSerDTO dto : dtos) {
					if (dto.getOrSerId().equals(entity.getOrSerId())) {
						if (entity.getUsers() != null) {
							dto.setOrSerUserId(entity.getUsers().getUsId());
							for (OrderSerDetail order : entity.getOrderserdetails()) {
								ServiceDetailsDTO serce = new ServiceDetailsDTO(order.getOrdSerServiceName(),order.getOrdSerServicePrice(),order.getSerce().getSeId());
								listDetails.add(serce);
							}
							dto.setlistSer(listDetails);
						} else {
							dto.setOrSerUserId(null);
						}
						dto.setOrSer_Total(entity.getOrSerTotal());

					}
				}
			}
			return new ResponseEntity<>(dtos, responseHeaders, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Connect server fail", responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/OrdersSer/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('ORDER_SERVICE') or hasRole('ADMIN')")
	public ResponseEntity<?> getById(@PathVariable("id") String id) {
		try {
			OrdersSer entity = service.getById(id);
			List<ServiceDetailsDTO> listDetails = new ArrayList<>();
			if (service.getById(id) != null) {
				OrdersSerDTO dto = modelMapper.map(entity, OrdersSerDTO.class);
				if(entity.getUsers() != null) {
					dto.setOrSerUserId(entity.getUsers().getUsId());
				}else {
					dto.setOrSerUserId(null);
				}
				for (OrderSerDetail order : entity.getOrderserdetails()) {
					ServiceDetailsDTO serce = new ServiceDetailsDTO(order.getOrdSerServiceName(),order.getOrdSerServicePrice(),order.getSerce().getSeId());
					listDetails.add(serce);
				}
				dto.setlistSer(listDetails);
				return new ResponseEntity<>(dto, responseHeaders, HttpStatus.OK);
			}
			return new ResponseEntity<>("Service order does not exist", responseHeaders, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>("Connect server fail", responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PostMapping(value = "/OrdersSer")
	public ResponseEntity<?> create(@RequestBody OrdersSerDTO dto) {
		try {
			if(dto.getOrSerPhoneNo() != null
			   && dto.getOrSerStartTime() != null
			   && dto.getOrSerEndTime() != null 
			   && dto.getOrSerPhoneNo() != null
			   && dto.getOrSer_Total() != null
			   && dto.getOrSerStatus() != null
			) {
				OrdersSer entityRequest = modelMapper.map(dto, OrdersSer.class);
				entityRequest.setOrSerId(idOrSerIentity());
				entityRequest.setUsers(UseSer.getById(dto.getOrSerUserId()));
				OrdersSer entity = service.create(entityRequest);
				for (ServiceDetailsDTO ser : dto.getlistSer()) {
					if(ser != null) {
						Serce serce = seceSer.getById(ser.getOrdSerServiceId());
						OrderSerDetail orderSerDetail = new OrderSerDetail(entity,serce,ser.getOrdSerServiceName(),ser.getOrdSerServicePrice());
						OrSerDeSer.create(orderSerDetail);
					}
				}			
				OrdersSerDTO dtoReponse = modelMapper.map(entity, OrdersSerDTO.class);
				if(entity.getUsers() != null) {
					dtoReponse.setOrSerUserId(entity.getUsers().getUsId());

				}else {
					dtoReponse.setOrSerUserId(null);
				}
				return new ResponseEntity<>("Success", responseHeaders, HttpStatus.CREATED);
			}else {
				return new ResponseEntity<>("Fail", responseHeaders, HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			return new ResponseEntity<>("Connect server fail", responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = "/OrdersSer/{id}")
	@PreAuthorize("hasRole('MODERATOR') and hasRole('ORDER_SERVICE') or hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<?> update(@PathVariable("id") String id, @RequestBody OrdersSerDTO dto) {
		try {
			System.out.print(id);
			if (service.getById(id) != null) {
				OrdersSer entityRequest = modelMapper.map(dto, OrdersSer.class);
				if(dto.getOrSerUserId() != null) {
					entityRequest.setUsers(UseSer.getById(dto.getOrSerUserId()));
				}	
				entityRequest.setOrSerId(id);
				OrdersSer entity = service.create(entityRequest);
				OrdersSerDTO dtoReponse = modelMapper.map(entity, OrdersSerDTO.class);
				if(dto.getOrSerUserId() != null) {
					dtoReponse.setOrSerUserId(entity.getUsers().getUsId());
				}		
				return new ResponseEntity<>("Success", responseHeaders, HttpStatus.OK);
			}else {
				return new ResponseEntity<>("Service order does not exist", responseHeaders, HttpStatus.NOT_FOUND);
			}		
		} catch (Exception e) {
			return new ResponseEntity<>("Connect server fail", responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping(value = "/OrdersSerArray")
	@PreAuthorize("hasRole('MODERATOR') and hasRole('ORDER_SERVICE') or hasRole('ADMIN')")
	public ResponseEntity<?> deleteArrayOrdersSer(@RequestBody String[] id) {
		try {
			for (String orSerId : id) {
				if (service.getById(orSerId) != null) {
					OrdersSer entity = service.getById(orSerId);
					for (OrderSerDetail item : entity.getOrderserdetails()) {
						OrSerDeSer.delete(item);
					}
					service.delete(entity);
				}
			}
			return new ResponseEntity<>(true, responseHeaders, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Connect server fail", responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping(value = "/OrdersSer")
	@PreAuthorize("hasRole('MODERATOR') and hasRole('ORDER_SERVICE') or hasRole('ADMIN')")
	public ResponseEntity<?> deleteOrdersSer(@RequestBody String id) {
		try {
			OrdersSer entity = service.getById(id);
			if (service.getById(id) != null) {
				for (OrderSerDetail item : entity.getOrderserdetails()) {
					OrSerDeSer.delete(item);
				}
				service.delete(entity);
				return new ResponseEntity<>("Success", responseHeaders, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Service order does not exist", responseHeaders, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Connect server fail", responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	public String idOrSerIentity() {
		LocalDate today = LocalDate.now();
		int numberUser = service.getCountOrSerByDate(today);
		int year = (today.getYear() % 100);
		String number = String.valueOf(numberUser);
		String id = null;
		switch (number.length()) {
		case 1:
			if (numberUser != 9) {
				id = "ORSER" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "000" + (numberUser + 1);
			} else {
				id = "ORSER" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "00" + (numberUser + 1);
			}
			break;
		case 2:
			if (numberUser != 99) {
				id = "ORSER" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "00" + (numberUser + 1);
			} else {
				id = "ORSER" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "0" + (numberUser + 1);
			}
			break;
		case 3:
			if (numberUser != 999) {
				id = "ORSER" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "0" + (numberUser + 1);
			} else {
				id = "ORSER" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "" + (numberUser + 1);
			}
			break;
		case 4:
			id = "ORSER" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "" + (numberUser + 1);
			break;
		default:
			System.out.print("Id error");
			break;
		}
		return id;
	}
}
