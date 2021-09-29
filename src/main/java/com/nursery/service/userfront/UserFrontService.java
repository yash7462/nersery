package com.nursery.service.userfront;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import com.nursery.config.ApiResponse;
import com.nursery.requestpayload.login.SignUpRequest;
import com.nursery.vo.userfront.UserFrontVo;

public interface UserFrontService {

	ApiResponse addUser(String firstName, String emailId, String passWord, String role, String age, String weight, String height,String gender);

	ApiResponse fetchUser();

	Optional<UserFrontVo> getUserInfoByuserFrontId(long userFrontId);

	ResponseEntity<?> signUpUserByPayload(@Valid SignUpRequest signUpRequest, BindingResult result);

	UserFrontVo findByEmailIdAndIsDeleted(String emailId, int i);
	
	UserFrontVo save(UserFrontVo userFrontVo);
}
