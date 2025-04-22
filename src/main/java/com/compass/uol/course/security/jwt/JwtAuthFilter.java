package com.compass.uol.course.security.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.compass.uol.course.services.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthFilter extends OncePerRequestFilter {

	private JwtService jwtService;
	private UserService userService;

	public JwtAuthFilter(JwtService jwtService, UserService userService) {
		this.jwtService = jwtService;
		this.userService = userService;
	}
	
	/*
	 * Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJCcnVub1ByZXNzaTIwMTJAZ21haWwuY29tIiwiZXhwIjoxNzQ0OTExODU5fQ.Ad-lsny80znHg4mB-Io_8amXKsheDXBLY614Li3Xk_5itAuibtJVJsQWQ-pSKQ_2qYy29Opy_huqVCUAjkMVig
	 */

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		String authorization = request.getHeader("Authorization");
		
		if (authorization != null && authorization.startsWith("Bearer")) {
			
			String token = authorization.split(" ")[1];
			boolean isValid = jwtService.tokenIsValid(token);
			
			if (isValid) {
				
				String emailUser = jwtService.getUserEmail(token);
				UserDetails userDetails = userService.loadUserByEmail(emailUser);
				
				UsernamePasswordAuthenticationToken user = 
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				
				user.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(user);;
				
			}
		}
		
		/*String path = request.getRequestURI();
		if (path.startsWith("/v3/api-docs") || path.startsWith("/swagger-ui")) {
		    filterChain.doFilter(request, response);
		    return;
		}*/
		
		filterChain.doFilter(request, response);
		
	}

}
