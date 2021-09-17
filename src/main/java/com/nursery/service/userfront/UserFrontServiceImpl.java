package com.nursery.service.userfront;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nursery.config.ApiResponse;
import com.nursery.repository.userfront.UserFrontRepository;
import com.nursery.repository.userrole.UserRoleRepository;
import com.nursery.vo.userfront.UserFrontVo;
import com.nursery.vo.userrole.UserRoleVo;

@Service
public class UserFrontServiceImpl implements UserFrontService {

	@Autowired
	UserFrontRepository userFrontRepository;
	
	@Autowired
	UserRoleRepository userRoleRepository;

	@Override
	public ApiResponse addUser(String firstName, String lastName, String emailId, String passWord,String role) {
		ApiResponse apiResponse = null;
		if (firstName != "") {
			if (lastName != "") {
				if (emailId != "") {
					if (passWord != "") {
						if (role != "") {
							UserRoleVo ur = userRoleRepository.findByUserRoleName(role);
							if(ur!=null) {
								BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
								String hashPassword = bCryptPasswordEncoder.encode(passWord);
								UserFrontVo user = userFrontRepository.findByEmailIdAndIsDeleted(emailId, 0);
								if(user == null) {
									UserFrontVo userFrontVo = new UserFrontVo();
									List<UserRoleVo> roles = new ArrayList<UserRoleVo>();
							        roles.add(ur);
							        userFrontVo.setRoles(roles);
									userFrontVo.setFirstName(firstName);
									userFrontVo.setLastName(lastName);
									userFrontVo.setEmailId(emailId);
									userFrontVo.setPassWord(hashPassword);
									UserFrontVo userfrontVo2 = userFrontRepository.save(userFrontVo);
									if (userfrontVo2 != null) {
										apiResponse = new ApiResponse(true, "SignUp Successfully", userfrontVo2.getEmailId());
									} else {
										apiResponse = new ApiResponse(false, "Internal Server Error", null);
									}
								}else {
									apiResponse = new ApiResponse(false, user.getEmailId()+" Already Registered", null);
								}
								
							}else {
								apiResponse = new ApiResponse(false, "UserRole Not Found", null);
							}
						}else {
							apiResponse = new ApiResponse(false, "UserRole Is Required", null);
						}
						
					} else {
						apiResponse = new ApiResponse(false, "Password Is Required", null);
					}
				} else {
					apiResponse = new ApiResponse(false, "Email Id Is Required", null);
				}
			} else {
				apiResponse = new ApiResponse(false, "Last Name Is Required", null);
			}
		} else {
			apiResponse = new ApiResponse(false, "First Name Is Required", null);
		}

		return apiResponse;
	}

	@Override
	public ApiResponse fetchUser() {
		ApiResponse apiResponse = null;
		List<UserFrontVo> userFrontVos = userFrontRepository.findAll();
		if(!userFrontVos.isEmpty()) {
			apiResponse = new ApiResponse(true, "Users Fetch SuccessFully", userFrontVos);
		}else {
			apiResponse = new ApiResponse(false, "No Users Found", null);
		}
		return apiResponse;
	}
	
	@Override
	public Optional<UserFrontVo> getUserInfoByuserFrontId(long userFrontId) {
		return userFrontRepository.findById(userFrontId);
	}
	
	

}
