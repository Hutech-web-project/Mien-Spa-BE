package com.example.mienspa.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mienspa.dto.OrderSerDetailDTO;
import com.example.mienspa.model.OrderSerDetail;
import com.example.mienspa.service.OrderSerDetailService;


@CrossOrigin
@RestController
@RequestMapping("/api")
public class OrderSerDetailController {
	
	@Autowired
	private OrderSerDetailService service;
	
	@Autowired
	private ModelMapper modelMapper;

	HttpHeaders responseHeaders = new HttpHeaders();
	@GetMapping(value = "/OrderSerDetail/{id}")
	public ResponseEntity<?> getAllByOrProId(@PathVariable("id") String id){
		try {
			if(service.getAllByOrSerId(id) != null) {
				List<OrderSerDetail> entityList = service.getAllByOrSerId(id);
				List<OrderSerDetailDTO> dtos = entityList.stream().map(user -> modelMapper.map(user, OrderSerDetailDTO.class))
						.collect(Collectors.toList());

				for (OrderSerDetail entity : entityList) {
					for (OrderSerDetailDTO dto : dtos) {
						if (dto.getOrdSerId().equals(entity.getOrdSerId())) {
							dto.setOrdersSerOrderId(entity.getOrdersser().getOrSerId());
							dto.setOrdSerServiceName(entity.getOrdSerServiceName());
							dto.setOrdSerServicePrice(entity.getOrdSerServicePrice());
						}
					}
				}
				return new ResponseEntity<>(dtos, responseHeaders, HttpStatus.OK);
			}else {
				return  new ResponseEntity<>("Service order does not exist", responseHeaders, HttpStatus.ACCEPTED);
			}			
		} catch (Exception e) {
			return new ResponseEntity<>("Connect server fail", responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
