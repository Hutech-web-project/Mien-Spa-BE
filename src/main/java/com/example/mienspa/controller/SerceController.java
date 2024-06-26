package com.example.mienspa.controller;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.mienspa.dto.SerceDTO;
import com.example.mienspa.model.Serce;
import com.example.mienspa.service.SerceService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiParam;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class SerceController {
	
	@Autowired
	private SerceService service;
	
	@Autowired
	private ModelMapper modelMapper;

	HttpHeaders responseHeaders = new HttpHeaders();
	Date date = Date.from(Instant.now());
	ObjectMapper mapper = new ObjectMapper();
	
	@GetMapping(value = "/Serce")
	public ResponseEntity<?> getAll(){
		try {
			return new ResponseEntity<>(
					service.getAll()
					.stream()
					.map(post -> modelMapper.map(post, SerceDTO.class))
					.collect(Collectors.toList()), 
					responseHeaders,
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Connect server fail", responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value = "/Serce/{id}")
	public ResponseEntity<?> getById(@PathVariable("id") String id){
		try {		 
			Serce entity = service.getById(id);
			if (service.getById(id) != null) {
				SerceDTO dto = modelMapper.map(entity, SerceDTO.class);
				return new ResponseEntity<>(dto, responseHeaders, HttpStatus.OK);
			}else {
				return  new ResponseEntity<>("This service does not exist", responseHeaders, HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			return new ResponseEntity<>("Connect server fail", responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PostMapping(value = "/Serce")
	@PreAuthorize("hasRole('MODERATOR') and hasRole('SERVICE') or hasRole('ADMIN')")
	public ResponseEntity<?> create(@RequestPart(required = false) String json,
			@RequestPart(required = false) @ApiParam(required = true, value = "") MultipartFile file) {		
		try {
			if (file == null) {

				SerceDTO dto = mapper.readValue(json, SerceDTO.class);
				Serce entityRequest = modelMapper.map(dto, Serce.class);
				entityRequest.setSeId(idSerceIdentity());
				entityRequest.setCreatedAt(date);
				Serce entity = service.create(entityRequest);
				SerceDTO dtoReponse = modelMapper.map(entity, SerceDTO.class);
				return new ResponseEntity<>(dtoReponse, responseHeaders, HttpStatus.CREATED);

			} else {
				SerceDTO dto = mapper.readValue(json, SerceDTO.class);
				Serce entityRequest = modelMapper.map(dto, Serce.class);
				entityRequest.setSeId(idSerceIdentity());		
				File folder = new File("Images/Services/"+entityRequest.getSeId());
				folder.mkdirs();
				Path path = Paths.get(folder.getPath());
				InputStream inputStream = file.getInputStream();
				Files.copy(inputStream, path.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
				entityRequest.setSeImage(entityRequest.getSeId()+"/"+file.getOriginalFilename().toLowerCase());
				entityRequest.setCreatedAt(date);
				Serce entity = service.create(entityRequest);
				SerceDTO dtoReponse = modelMapper.map(entity, SerceDTO.class);
				return new ResponseEntity<>(dtoReponse, responseHeaders, HttpStatus.CREATED);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Connect server fail", responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping(value = "/Serce/{id}")
	@PreAuthorize("hasRole('MODERATOR') and hasRole('SERVICE') or hasRole('ADMIN')")
	public ResponseEntity<?> update(@PathVariable("id") String id, @RequestPart(required = false) String json,
			@RequestPart(required = false) @ApiParam(required = true, value = "") MultipartFile file) {		
		try {	
			if (service.getById(id) != null) {
				if (file == null) {
					SerceDTO dto = mapper.readValue(json, SerceDTO.class);
					Serce entityRequest = modelMapper.map(dto, Serce.class);
					entityRequest.setSeId(id);
					entityRequest.setUpdatedAt(date);
					Serce entity = service.create(entityRequest);
					SerceDTO dtoReponse = modelMapper.map(entity, SerceDTO.class);
					return new ResponseEntity<>(dtoReponse, responseHeaders, HttpStatus.NOT_FOUND);
				} else {				
					SerceDTO dto = mapper.readValue(json, SerceDTO.class);
					//delete old image
					if(dto.getSeImage() != null) {
						File directoryToDelete = new File("Images/Services/"+id);
						FileSystemUtils.deleteRecursively(directoryToDelete);
					}
					
					Serce entityRequest = modelMapper.map(dto, Serce.class);
					File folder=new File("Images/Services/"+entityRequest.getSeId());
					folder.mkdirs();
					Path path = Paths.get(folder.getPath());
					InputStream inputStream = file.getInputStream();
					Files.copy(inputStream, path.resolve(file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
					entityRequest.setSeImage(entityRequest.getSeId()+"/"+file.getOriginalFilename().toLowerCase());
					entityRequest.setSeId(id);
					entityRequest.setUpdatedAt(date);
					Serce entity = service.create(entityRequest);
					SerceDTO dtoReponse = modelMapper.map(entity, SerceDTO.class);
					return new ResponseEntity<>(dtoReponse, responseHeaders, HttpStatus.NOT_FOUND);

				}
			}else {
				return new ResponseEntity<>("This service does not exist", responseHeaders, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Connect server fail", responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@DeleteMapping(value = "/Serce")
	@PreAuthorize("hasRole('MODERATOR') and hasRole('SERVICE') or hasRole('ADMIN')")
	public ResponseEntity<?> deleteSerce(@RequestBody String id) {
		try {
			Serce entity = service.getById(id);
			if (service.getById(id) != null) {
				if(entity.getSeImage() != null) {
					File directoryToDelete = new File("Images/Services/"+entity.getSeId());
					FileSystemUtils.deleteRecursively(directoryToDelete);
				}
					service.delete(entity);
					return new ResponseEntity<>("Success", responseHeaders, HttpStatus.OK);
			}else {
				return  new ResponseEntity<>("This service does not exist", responseHeaders, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Connect server fail", responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public String idSerceIdentity() {
		LocalDate today = LocalDate.now();
		int numberUser = service.getCountSerByDate(today);
		int year = (today.getYear() % 100);
		String number = String.valueOf(numberUser);
		String id = null;
		switch (number.length()) {
		case 1:
			if (numberUser != 9) {
				id = "S" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "000" + (numberUser + 1);
			} else {
				id = "S" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "00" + (numberUser + 1);
			}
			break;
		case 2:
			if (numberUser != 99) {
				id = "S" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "00" + (numberUser + 1);
			} else {
				id = "S" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "0" + (numberUser + 1);
			}
			break;
		case 3:
			if (numberUser != 999) {
				id = "S" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "0" + (numberUser + 1);
			} else {
				id = "S" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "" + (numberUser + 1);
			}
			break;
		case 4:
			id = "S" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "" + (numberUser + 1);
			break;
		default:
			System.out.print("Id error");
			break;
		}
		return id;
	}
}
