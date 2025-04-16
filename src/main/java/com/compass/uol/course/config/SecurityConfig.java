package com.compass.uol.course.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
    @Bean
    UserDetailsService userDetailsService() {
        UserDetails user = User
            .withUsername("username")
            .password(passwordEncoder().encode("123"))
            .roles("USER")
            .build();
        return new InMemoryUserDetailsManager(user);
    }
	
	/*@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


	}*/

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
