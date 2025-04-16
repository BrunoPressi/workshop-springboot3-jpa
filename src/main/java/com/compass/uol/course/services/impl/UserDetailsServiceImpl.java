package com.compass.uol.course.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		if(!username.equals("userName")) {
			throw new UsernameNotFoundException("User not found");
		}
		
		return User
				.builder()
				.username("userName")
				.password(passwordEncoder.encode("123"))
				.roles("USER", "ADMIN")
				.build();
	}


}
