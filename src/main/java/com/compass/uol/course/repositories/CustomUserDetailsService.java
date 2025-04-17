package com.compass.uol.course.repositories;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface CustomUserDetailsService extends UserDetailsService {
	
	UserDetails loadUserByEmail(String email);

}
