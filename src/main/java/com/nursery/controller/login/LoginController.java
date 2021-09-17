package com.nursery.controller.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nursery.config.ApiResponse;
import com.nursery.service.userfront.UserFrontService;

import lombok.Data;
@Data
@Controller()
@RequestMapping("/api/auth")
public class LoginController {
	
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
	
	@GetMapping("/getusers")
	@ResponseBody
	public ResponseEntity<ApiResponse> fetchAllUser(){
		ApiResponse apiResponse = null;
		apiResponse = userFrontService.fetchUser();
		return ResponseEntity.ok(apiResponse);
	}
	
}
