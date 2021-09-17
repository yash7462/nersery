package com.nursery.securityconfig;

import lombok.Data;

@Data
public class LoginRequestPayload {

	//@NotBlank(message = "Username is required.")
	private String username;

	//@NotBlank(message = "Password is required.")
	private String password;
}
