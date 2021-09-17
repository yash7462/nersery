package com.nursery.service.userfront;

import com.nursery.config.ApiResponse;

public interface UserFrontService {

	ApiResponse addUser(String firstName, String lastName, String emailId, String passWord, String role);

	ApiResponse fetchUser();
}
