package com.hexaware.app.Service;

import com.hexaware.app.Dto.SignupRequest;
import com.hexaware.app.Dto.User;

public interface AuthService {
	User createUser(SignupRequest signupRequest);

	boolean hasCustomerWithEmail(String email);

	
}
