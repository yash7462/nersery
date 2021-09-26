package com.nursery.service.userfront;

import java.util.Optional;

import com.nursery.config.ApiResponse;
import com.nursery.vo.userfront.UserFrontVo;

public interface UserFrontService {

	ApiResponse addUser(String firstName, String emailId, String passWord, String role, String age, String weight, String height,String gender);

	ApiResponse fetchUser();

	Optional<UserFrontVo> getUserInfoByuserFrontId(long userFrontId);
}
