package com.hexaware.app.Service;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
	UserDetailsService userDetailsService();
}
