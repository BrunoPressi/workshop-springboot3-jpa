package com.compass.uol.course.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.compass.uol.course.services.impl.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private UserDetailsServiceImpl userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Bean
	AuthenticationManager authManager(HttpSecurity http) throws Exception {
	    return http.getSharedObject(AuthenticationManagerBuilder.class)
	            .userDetailsService(userService) // Aqui você configura o UserDetailsService
	            .passwordEncoder(passwordEncoder) // Aqui você configura o password encoder
	            .and()
	            .build();
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable()) // Desativa CSRF
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/products/**").hasRole("ADMIN") // Permite somente o admin acessar a página
                        .requestMatchers("/users/**").hasAnyRole("USER", "ADMIN") // Permite ao usuário que tem alguma dessas roles acessar a página
                        .requestMatchers("/orders/**").hasAnyRole("USER", "ADMIN"))
                .httpBasic(withDefaults());  // Método de autenticação Basic

	    return http.build();

	}

}
