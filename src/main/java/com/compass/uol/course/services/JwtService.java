package com.compass.uol.course.services;

import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import com.compass.uol.course.CourseApplication;
import com.compass.uol.course.entities.UserEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	@Value("${security.jwt.expiration}")
	private String expiration;
	
	// @Value("${security.jwt.signature-key}")
	 //private String signatureKey;
	
	private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

		
	public String generateToken(UserEntity user ) {
		
		long expiration = Long.valueOf(this.expiration);
		LocalDateTime expirationDateTime = LocalDateTime.now().plusMinutes(expiration);
		Instant instant = expirationDateTime.atZone(ZoneId.systemDefault()).toInstant();
		Date date = Date.from(instant);
				
		/*HashMap<String, Object> claims = new HashMap<>();
		claims.put("roles", "admin");*/
		
		return Jwts.builder()
                .setSubject(user.getEmail()) // Sujeito do token
                .setExpiration(date) // Data de expiração
                //.setClaims(claims) // claims customizadas
                .signWith(key, SignatureAlgorithm.HS512) // Assinatura do token
                .compact(); // Geração final do token compactado
	}
	
	private Claims getClaims(String token) throws ExpiredJwtException {
	    return Jwts
	            .parserBuilder()  // Usando parserBuilder ao invés de parser
	            .setSigningKey(key)  // Defina a chave de assinatura
	            .build()
	            .parseClaimsJws(token)  // Parse o JWT e extraia os claims
	            .getBody();  // Retorne os claims
	}
	
	public boolean tokenIsValid(String token) {
		try {
			Claims claims = getClaims(token);
			Date date = claims.getExpiration();
			
			LocalDateTime dateExpiration = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
			
			return !LocalDateTime.now().isAfter(dateExpiration);
			
		}
		catch(Exception e) {
			return false;
		}
	}
	
	public String getUserData(String token) throws ExpiredJwtException{
		return (String) getClaims(token).getSubject();
	}
	
	public static void main(String[] arg) {
		
		ConfigurableApplicationContext context = SpringApplication.run(CourseApplication.class);
		
		JwtService service = context.getBean(JwtService.class);
		UserEntity user = new UserEntity();
		user.setEmail("BrunoPressi2012@gmail.com");
		String token = service.generateToken(user);
		System.out.println(token);
		
		System.out.println("Token is valid? " + service.tokenIsValid(token));
		System.out.println("User Data: " + service.getUserData(token));
		
	}
	
}
