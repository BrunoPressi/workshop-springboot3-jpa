package com.compass.uol.course.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	UserDetailsService userDetailsService() {
		UserDetails user = User.withUsername("username")
				.password(passwordEncoder()
				.encode("123"))
				.roles("USER", "ADMIN")
				.build();
		return new InMemoryUserDetailsManager(user); // Cria um usuário na memória
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable()) // Desativa CSRF
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/products/**").hasRole("ADMIN") // Permite somente o admin acessar a página
                        .requestMatchers("/users/**").hasAnyRole("USER", "ADMIN") // Permite ao usuário que tem alguma dessas roles acessar a página
                        .requestMatchers("/orders/**").hasAnyRole("USER", "ADMIN")
                		.requestMatchers("/h2-console/**").permitAll())
                .httpBasic(withDefaults());  // Método de autenticação Basic

	    return http.build();

	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
