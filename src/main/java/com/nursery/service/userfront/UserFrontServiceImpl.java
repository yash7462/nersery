package com.nursery.service.userfront;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;

import com.nursery.config.ApiResponse;
import com.nursery.config.ErrorValidationConfig;
import com.nursery.repository.userfront.UserFrontRepository;
import com.nursery.repository.userrole.UserRoleRepository;
import com.nursery.requestpayload.login.SignUpRequest;
import com.nursery.vo.userfront.UserFrontVo;
import com.nursery.vo.userrole.UserRoleVo;

import lombok.extern.java.Log;
@Log
@Service
public class UserFrontServiceImpl implements UserFrontService {

	@Autowired
	UserFrontRepository userFrontRepository;

	@Autowired
	UserRoleRepository userRoleRepository;
	
	@Autowired
	ErrorValidationConfig errorValidationConfig;

	@Override
	public ApiResponse addUser(String firstName, String emailId, String passWord, String role, String age,
			String weight, String height, String gender) {
		ApiResponse apiResponse = null;
		try {
			if (StringUtils.isNotBlank(firstName)) {
				if (StringUtils.isNotBlank(emailId)) {
					if (StringUtils.isNotBlank(passWord)) {
						if (StringUtils.isNotBlank(role)) {
							if (StringUtils.isNotBlank(age)) {
								try {
									int parse_age = Integer.parseInt(age);
									
									if (StringUtils.isNotBlank(height)) {
										try {
											double parse_height = Double.parseDouble(height);	
											if (StringUtils.isNotBlank(weight)) {
												try {
													double parse_weight = Double.parseDouble(weight);	
													if (StringUtils.isNotBlank(gender)) {

														UserRoleVo ur = userRoleRepository.findByUserRoleName(role);
														if (ur != null) {
															BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
															String hashPassword = bCryptPasswordEncoder.encode(passWord);
															UserFrontVo user = userFrontRepository.findByEmailIdAndIsDeleted(emailId,
																	0);
															if (user == null) {
																UserFrontVo userFrontVo = new UserFrontVo();
																List<UserRoleVo> roles = new ArrayList<UserRoleVo>();
																roles.add(ur);
																userFrontVo.setRoles(roles);
																userFrontVo.setFirstName(firstName);
																userFrontVo.setEmailId(emailId);
																userFrontVo.setPassWord(hashPassword);
																userFrontVo.setAge(parse_age);
																userFrontVo.setGender(gender);
																userFrontVo.setHeight(parse_height);
																userFrontVo.setWeight(parse_weight);
																try {
																	UserFrontVo userfrontVo2 = userFrontRepository.save(userFrontVo);
																	if (userfrontVo2 != null) {
																		apiResponse = new ApiResponse(true, "SignUp Successfully",
																				userfrontVo2.getEmailId());
																	} else {
																		apiResponse = new ApiResponse(false, "Internal Server Error",
																				null);
																	}
																} catch (Exception e) {
																	// TODO: handle exception
																}

															} else {
																apiResponse = new ApiResponse(false,
																		user.getEmailId() + " Already Registered", null);
															}

														} else {
															apiResponse = new ApiResponse(false, "UserRole Not Found", null);
														}
													} else {
														apiResponse = new ApiResponse(false, "Gender Is Required", null);
													}
												} catch (Exception e) {
													e.printStackTrace();
													apiResponse = new ApiResponse(false, "Weight must be numeric value", null);
												}
											} else {
												apiResponse = new ApiResponse(false, "Weight Is Required", null);
											}
										} catch (Exception e) {
											e.printStackTrace();
											apiResponse = new ApiResponse(false, "Height must be numeric value", null);
										}
									} else {
										apiResponse = new ApiResponse(false, "Height Is Required", null);
									}
								} catch (Exception e) {
									apiResponse = new ApiResponse(false, "Age must be numeric value", null);
								}
								
							} else {
								apiResponse = new ApiResponse(false, "Age Is Required", null);
							}
						} else {
							apiResponse = new ApiResponse(false, "UserRole Is Required", null);
						}

					} else {
						apiResponse = new ApiResponse(false, "Password Is Required", null);
					}
				} else {
					apiResponse = new ApiResponse(false, "Email Id Is Required", null);
				}

			} else {
				apiResponse = new ApiResponse(false, "First Name Is Required", null);
			}
		}catch (Exception e) {
			apiResponse = new ApiResponse(false, "Internal Server Error", null);
		}
		

		return apiResponse;
	}

	@Override
	public ApiResponse fetchUser() {
		ApiResponse apiResponse = null;
		List<UserFrontVo> userFrontVos = userFrontRepository.findAll();
		if (!userFrontVos.isEmpty()) {
			apiResponse = new ApiResponse(true, "Users Fetch SuccessFully", userFrontVos);
		} else {
			apiResponse = new ApiResponse(false, "No Users Found", null);
		}
		return apiResponse;
	}

	@Override
	public Optional<UserFrontVo> getUserInfoByuserFrontId(long userFrontId) {
		return userFrontRepository.findById(userFrontId);
	}

	@Override
	public ResponseEntity<?> signUpUserByPayload(@Valid @RequestBody SignUpRequest signUpRequest, BindingResult result) {
		ApiResponse apiResponse = null;
		try {
			
			Map<String, String> map = errorValidationConfig.validateRequest(result);
			if(map!=null && !map.isEmpty()) {//if Request payload has some error then it will generate error
				//log.warning("map size---->"+map.size());
				apiResponse = new ApiResponse(false, "Validation Failed", map);
				return ResponseEntity.badRequest().body(apiResponse);
			}else {//if request payload has no error then execute this bloack
				
				//Default user role is ROLE_USER
				String role = "ROLE_USER";
				if(StringUtils.isNotBlank(signUpRequest.getRole())) {
					role = signUpRequest.getRole();
				}
				
				UserRoleVo ur = userRoleRepository.findByUserRoleName(role);
				List<UserRoleVo> roles = new ArrayList<UserRoleVo>();
				if (ur != null) {//if user role found then it will added in role set
					roles.add(ur);
				}else {//if not found user role then give error
					apiResponse = new ApiResponse(false, "UserRole Not Found", null);
					return ResponseEntity.badRequest().body(apiResponse);
				}
				
				//check for username is already exist or not. here username is emailId meand email must be unique
				UserFrontVo user = userFrontRepository.findByEmailIdAndIsDeleted(signUpRequest.getEmailId(),0);
				if (user != null) {//username found then give error
					apiResponse = new ApiResponse(false, user.getEmailId() + " Already Registered", null);
					return ResponseEntity.badRequest().body(apiResponse);
					
				} else {//username not found meand fresh entry
					BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
					String hashPassword = bCryptPasswordEncoder.encode(signUpRequest.getPassWord());
					
					UserFrontVo userFrontVo = new UserFrontVo();
//					List<UserRoleVo> roles = new ArrayList<UserRoleVo>();
//					roles.add(ur);
					userFrontVo.setRoles(roles);
					userFrontVo.setFirstName(signUpRequest.getFirstName());
					userFrontVo.setLastName(signUpRequest.getLastName());
					userFrontVo.setEmailId(signUpRequest.getEmailId());
					userFrontVo.setPassWord(hashPassword);
					userFrontVo.setAge(signUpRequest.getAge());
					userFrontVo.setGender(signUpRequest.getGender());
					userFrontVo.setHeight(signUpRequest.getHeight());
					userFrontVo.setWeight(signUpRequest.getWeight());
					try {
						UserFrontVo userfrontVo2 = userFrontRepository.save(userFrontVo);
						if (userfrontVo2 != null) {
							apiResponse = new ApiResponse(true, "SignUp Successfully",
									userfrontVo2.getEmailId());
							return ResponseEntity.ok(apiResponse);
						} else {
							apiResponse = new ApiResponse(false, "Internal Server Error - User SignUp Failed",null);
							return ResponseEntity.badRequest().body(apiResponse);
						}
					} catch (Exception e) {
						e.printStackTrace();
						apiResponse = new ApiResponse(false, "Internal Server Error in SignUp",null);
						return ResponseEntity.badRequest().body(apiResponse);
						
					}
				}
			}
			

		} catch (Exception e) {
			e.printStackTrace();
			apiResponse = new ApiResponse(false, "Internal Server Error in SignUp",null);
			return ResponseEntity.badRequest().body(apiResponse);
		}
				
	}

	@Override
	public UserFrontVo findByEmailIdAndIsDeleted(String emailId, int i) {
		// TODO Auto-generated method stub
		return userFrontRepository.findByEmailIdAndIsDeleted(emailId, i);
	}

	@Override
	public UserFrontVo save(UserFrontVo userFrontVo) {
		// TODO Auto-generated method stub
		return userFrontRepository.save(userFrontVo);
	}
	
	

}
