package com.example.mienspa.controller;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.mienspa.dto.request.LoginRequest;
import com.example.mienspa.dto.request.SignupRequest;
import com.example.mienspa.dto.request.TokenRefreshRequest;
import com.example.mienspa.dto.response.JwtResponse;
import com.example.mienspa.dto.response.MessageResponse;
import com.example.mienspa.dto.response.TokenRefreshResponse;
import com.example.mienspa.jwt.JwtUtils;
import com.example.mienspa.model.ERole;
import com.example.mienspa.model.RefreshToken;
import com.example.mienspa.model.UserDetailsImpl;
import com.example.mienspa.model.UserRole;
import com.example.mienspa.model.Users;
import com.example.mienspa.service.RefreshTokenService;
import com.example.mienspa.service.RoleService;
import com.example.mienspa.service.UserRoleService;
import com.example.mienspa.service.UserService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@CrossOrigin
@RestController
@RequestMapping("/login")
public class AuthController {
	@Value("${bezkoder.app.jwtSecret}")
	private String jwtSecret;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserService userService;

	@Autowired
	RoleService roleService;

	@Autowired
	UserRoleService userRoleService;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	RefreshTokenService refreshTokenService;

	HttpHeaders responseHeaders = new HttpHeaders();

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
		try {
			Users user = userService.getByEmail(loginRequest.getEmail());
			if (user != null) {
				Authentication authentication = authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(user.getUsEmailNo(), loginRequest.getPassword()));
				SecurityContextHolder.getContext().setAuthentication(authentication);
				UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
				String jwt = jwtUtils.generateJwtToken(userDetails);
				List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
						.collect(Collectors.toList());
				RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId(), jwt);
				JwtResponse jwtResponse = new JwtResponse(jwt, refreshToken.getJwtId(), userDetails.getId(),
						userDetails.getUsername(), userDetails.getEmail(), roles);
				return new ResponseEntity<>(jwtResponse, responseHeaders, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Email or Password error", responseHeaders, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Email or Password error", responseHeaders, HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/refreshtoken")
	public ResponseEntity<?> refreshtoken(@RequestBody TokenRefreshRequest request) {

		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(request.getToken());
			return new ResponseEntity<>("JWT token is not expired: {}", responseHeaders, HttpStatus.NOT_FOUND);
		} catch (SignatureException e) {
			return new ResponseEntity<>("Invalid JWT signature: {}", responseHeaders, HttpStatus.NOT_FOUND);
		} catch (MalformedJwtException e) {
			return new ResponseEntity<>("Invalid JWT token: {}", responseHeaders, HttpStatus.NOT_FOUND);
		} catch (ExpiredJwtException e) {
			Date date = Date.from(Instant.now());
			String requestRefreshToken = request.getRefreshToken();
			String refreshToken = request.getRefreshToken();
			RefreshToken entity = refreshTokenService.findByToken(refreshToken);
			if (entity == null) {
				return new ResponseEntity<>("Token does not exist", responseHeaders, HttpStatus.NOT_FOUND);
			} else if (entity.getJwtId().equals(request.getRefreshToken()) == false) {
				return new ResponseEntity<>("Refresh Token has not yet expired", responseHeaders, HttpStatus.NOT_FOUND);
			} else if (entity.isIsUsed()) {
				return new ResponseEntity<>("Refresh Token has been used", responseHeaders, HttpStatus.NOT_FOUND);
			} else if (entity.isIsRevorked()) {
				return new ResponseEntity<>("Refresh Token has been revoked", responseHeaders, HttpStatus.NOT_FOUND);
			} else if (entity.getExpiryDate().compareTo(date) < 0) {
				return new ResponseEntity<>("Refresh Token expired", responseHeaders, HttpStatus.NOT_FOUND);
			} else {
				entity.setIsUsed(true);
				refreshTokenService.save(entity);
				Users user = userService.getById(entity.getUsers().getUsId());
				String tokenResponse = jwtUtils.generateTokenFromUsername(user.getUsUserName(), user.getUsId(),
						user.getUsEmailNo());
				return new ResponseEntity<>(new TokenRefreshResponse(tokenResponse, requestRefreshToken),
						responseHeaders, HttpStatus.OK);
			}

		} catch (UnsupportedJwtException e) {
			return new ResponseEntity<>("JWT token is unsupported: {}", responseHeaders, HttpStatus.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>("JWT claims string is empty: {}", responseHeaders, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity<>(null, responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
		if (testUsingStrictRegex(signUpRequest.getEmail())) {
			if (userService.checkMail(signUpRequest.getEmail())) {
				return new ResponseEntity<>("Error: Email is already in use!", responseHeaders, HttpStatus.NOT_FOUND);
			}
			try {
				if(signUpRequest.getEmail() != null && signUpRequest.getPassword()!= null && signUpRequest.getUsername()!= null) {
					Users user = new Users(idUserIentity(), signUpRequest.getUsername(),
							encoder.encode(signUpRequest.getPassword()), signUpRequest.getEmail());
					Date date = Date.from(Instant.now());
					Set<UserRole> roles = new HashSet<>();
					user.setIsAdmin(false);
					UserRole userRole = new UserRole();
					userRole.setUsers(user);
					userRole.setRole(roleService.getByName(ERole.ROLE_USER.toString()));
					roles.add(userRole);
					userService.create(user);
					for (UserRole role : roles) {
						userRoleService.create(role);
					}
					user.setCreatedAt(date);
					user.setUserRoles(roles);
					userService.create(user);
					return new ResponseEntity<>("Successfully", responseHeaders, HttpStatus.CREATED);
				}else {
					return new ResponseEntity<>("Fail", responseHeaders, HttpStatus.NOT_FOUND);
				}

			} catch (Exception e) {
				return new ResponseEntity<>("Connect server fail", responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
			}		
		}else {
			return new ResponseEntity<>("Invalid email", responseHeaders, HttpStatus.NOT_FOUND);
		}
		
	}

	public String idUserIentity() {
		LocalDate today = LocalDate.now();
		int numberUser = userService.getCountUserByDate(today);
		int year = (today.getYear() % 100);
		String number = String.valueOf(numberUser);
		String id = null;
		switch (number.length()) {
		case 1:
			if (numberUser != 9) {
				id = "MS" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "000" + (numberUser + 1);
			} else {
				id = "MS" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "00" + (numberUser + 1);
			}
			break;
		case 2:
			if (numberUser != 99) {
				id = "MS" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "00" + (numberUser + 1);
			} else {
				id = "MS" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "0" + (numberUser + 1);
			}
			break;
		case 3:
			if (numberUser != 999) {
				id = "MS" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "0" + (numberUser + 1);
			} else {
				id = "MS" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "" + (numberUser + 1);
			}
			break;
		case 4:
			id = "MS" + today.getDayOfMonth() + "" + today.getMonthValue() + "" + year + "" + (numberUser + 1);
			break;
		default:
			System.out.print("Id error");
			break;
		}
		return id;
	}

	public boolean testUsingStrictRegex(String emai) {;
	    String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" 
	        + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
	    return Pattern.compile(regexPattern)
	    	      .matcher(emai)
	    	      .matches();
	}

}
