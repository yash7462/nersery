package com.nursery.requestpayload.login;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class SignUpRequest {

	@NotBlank(message = "First Name Is Required")
	private String firstName;
	
	private String lastName;
	
	@NotBlank(message = "Email Id Is Required")
	@Email(message = "Email Id is not Valid, please enter valid Email Id")
	private String emailId;
	
	@NotBlank(message = "PassWord Is Required")
	@Size(min = 6,max = 20,message = "PassWord must be between 6 to 20 characters.")
	private String passWord;
	
	@NotNull(message = "Age Is Required")
    @Min(value = 1 , message = "Age should be greater then then equal to 1")
	private int age;
	
	@NotBlank(message = "Gender Is Required")
	private String gender;
	
	@NotNull(message = "Height Is Required")
	@Min(value = 1 , message = "Height should be greater then then equal to 1")
	private double height;
	
	@NotNull(message = "Weight Is Required")
	@Min(value = 1 , message = "Weight should be greater then then equal to 1")
	private double weight;
	
	private String role;
	
}
