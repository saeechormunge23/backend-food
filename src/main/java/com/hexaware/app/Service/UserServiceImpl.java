package com.hexaware.app.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hexaware.app.Dao.UserRepository;
import com.hexaware.app.Dto.User;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
    private UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findFirstByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return user; // User entity should implement UserDetails
	}

	@Override
	public UserDetailsService userDetailsService() {
		// TODO Auto-generated method stub
				return this;
	}

}
