package com.compass.uol.course.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import com.compass.uol.course.security.jwt.JwtAuthFilter;
import com.compass.uol.course.security.jwt.JwtService;
import com.compass.uol.course.services.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtService jwtService;
	
	@Bean
	AuthenticationManager authManager(HttpSecurity http) throws Exception {
	    AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
	    authBuilder.userDetailsService(userService).passwordEncoder(passwordEncoder);
	    return authBuilder.build();
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(HttpMethod.GET, "/products/**").authenticated()
                        .requestMatchers("/users/**").permitAll()
                        .requestMatchers(
                        		"/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html").permitAll()
                        .requestMatchers("/orders/**").hasAnyRole("USER", "ADMIN")
                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class));

	    return http.build();

	}
	
	@Bean
	OncePerRequestFilter jwtFilter() {
		return new JwtAuthFilter(jwtService, userService);
	}

}
