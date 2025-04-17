package com.compass.uol.course.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.compass.uol.course.dto.CredentialDTO;
import com.compass.uol.course.dto.TokenDTO;
import com.compass.uol.course.entities.UserEntity;
import com.compass.uol.course.security.jwt.JwtService;
import com.compass.uol.course.services.UserService;
import com.compass.uol.course.services.exception.InvalidPasswordException;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value="/users")
public class UserResource {
	
	@Autowired
	private UserService service;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtService jwtService;
	
	@GetMapping
	public ResponseEntity<List<UserEntity>> findAll() { 
		List<UserEntity> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<UserEntity> findById(@PathVariable Long id) {
		UserEntity u = service.findById(id);
		return ResponseEntity.ok().body(u);
	}
	
	@PostMapping
	public ResponseEntity<UserEntity> insert(@RequestBody @Valid UserEntity obj) {
		String encryptedPassword = passwordEncoder.encode(obj.getPassword());
		obj.setPassword(encryptedPassword);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(obj); 
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<UserEntity> update(@PathVariable Long id, @RequestBody UserEntity obj) {
		obj = service.update(id, obj);
		return ResponseEntity.ok().body(obj);
	}
	
	@PostMapping("/auth")
	public TokenDTO auth(@RequestBody CredentialDTO credentials) {
		
		UserEntity user = new UserEntity();
		user.setEmail(credentials.getEmail());
		user.setPassword(credentials.getPassword());
		
		try {
			UserDetails userAuthenticated = service.authenticate(user);
			String token = jwtService.generateToken(user);
			return new TokenDTO(user.getEmail(), token);
		}
		catch(UsernameNotFoundException | InvalidPasswordException e ) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED , e.getMessage());
		}

	}
	
}
