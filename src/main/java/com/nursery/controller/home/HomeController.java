package com.nursery.controller.home;

import java.util.Map;
import java.util.Optional;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nursery.config.ApiResponse;
import com.nursery.securityconfig.RequestUserDetails;
import com.nursery.service.userfront.UserFrontService;
import com.nursery.vo.userfront.UserFrontVo;
@Controller
@RequestMapping("/home")
public class HomeController {
	
	@Autowired
	UserFrontService userFrontService;
	
	@GetMapping("/profile")
	@ResponseBody
	public ResponseEntity<ApiResponse> getAllProfile(Authentication authentication){
		
		ApiResponse apiResponse = null;
		System.out.println("corporateId :"+((RequestUserDetails) authentication.getPrincipal()).getCorporateId());
		System.out.println("companyId :"+((RequestUserDetails) authentication.getPrincipal()).getCompanyId());
		System.out.println("branchId :"+((RequestUserDetails) authentication.getPrincipal()).getBranchId());
		System.out.println("userFrontId :"+((RequestUserDetails) authentication.getPrincipal()).getUserFrontId());
		System.out.println("roleId :"+((RequestUserDetails) authentication.getPrincipal()).getRoleId());
		System.out.println("userType :"+((RequestUserDetails) authentication.getPrincipal()).getUserType());
		System.out.println("name :"+((RequestUserDetails) authentication.getPrincipal()).getName());
		RequestUserDetails requestUserDetails = (RequestUserDetails) authentication.getPrincipal();
		Map<String, Object> map = new HashedMap<>();
		if(requestUserDetails.getUserFrontId()!=null) {
			Optional<UserFrontVo> userFront  = userFrontService.getUserInfoByuserFrontId(requestUserDetails.getUserFrontId());
			if(userFront.isPresent()) {
				UserFrontVo userFrontVo = userFront.get();
				map.put("firstName", userFrontVo.getFirstName());
				map.put("lastName", userFrontVo.getLastName());
				map.put("emailId", userFrontVo.getEmailId());
				map.put("role", userFrontVo.getRoles().get(0).getUserRoleName());
				apiResponse = new ApiResponse(true, "User Details Found", map);
			}else {
				apiResponse = new ApiResponse(true, "User Details not Found", map);
			}
		}else {
			apiResponse = new ApiResponse(true, "User Object is not Valid", map);
		}
		
		return ResponseEntity.ok(apiResponse);
	}

}
