package com.nursery.controller.login;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nursery.config.ApiResponse;
import com.nursery.securityconfig.JwtProvider;
import com.nursery.securityconfig.JwtResponse;
import com.nursery.securityconfig.LoginRequestPayload;
import com.nursery.service.userfront.UserFrontService;

import lombok.Data;
@Data
@Controller()
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtProvider jwtProvider;
	
	@Autowired
	UserFrontService userFrontService;

	@PostMapping("/signup")
	@ResponseBody
	public ResponseEntity<ApiResponse> signUpUser(@RequestParam(name="firstName",defaultValue = "",required = true) String firstName,
			@RequestParam(name="lastName",defaultValue = "",required = true) String lastName,
			@RequestParam(name="emailId",defaultValue = "",required = true) String emailId,
			@RequestParam(name="passWord",defaultValue = "",required = true) String passWord,
			@RequestParam(name="role",defaultValue = "",required = true) String role){
		ApiResponse apiResponse = null;
		
		apiResponse = userFrontService.addUser(firstName,lastName,emailId,passWord,role);
		return ResponseEntity.ok(apiResponse);
	}

	@PostMapping("/signin")
	@ResponseBody
	public ResponseEntity<ApiResponse> authenticateUser(@Valid @RequestBody LoginRequestPayload loginRequest, BindingResult result) {
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = jwtProvider.generateJwtToken(authentication);
		ApiResponse apiResponse = null;
		if(StringUtils.isNotBlank(jwt)) {
			apiResponse = new ApiResponse(true, "Token Generated Successfully", new JwtResponse(jwt));	
		}else {
			apiResponse = new ApiResponse(false, "Token not Generated", null);
		}
		
		return ResponseEntity.ok(apiResponse);
		
//		Authentication authentication = authenticationManager.authenticate(
//				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
//		
//		SecurityContextHolder.getContext().setAuthentication(authentication);
//		String jwt = jwtUtils.generateJwtToken(authentication);
//		
//		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
//		List<String> roles = userDetails.getAuthorities().stream()
//				.map(item -> item.getAuthority())
//				.collect(Collectors.toList());
//		
//		return ResponseEntity.ok(new JwtResponse(jwt, 
//				 userDetails.getId(), 
//				 userDetails.getUsername(), 
//				 userDetails.getEmail(), 
//				 roles));
	}
	
	@GetMapping("/getusers")
	@ResponseBody
	public ResponseEntity<ApiResponse> fetchAllUser(){
		ApiResponse apiResponse = null;
		apiResponse = userFrontService.fetchUser();
		return ResponseEntity.ok(apiResponse);
	}
	
}
