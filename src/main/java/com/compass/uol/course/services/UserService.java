package com.compass.uol.course.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.compass.uol.course.entities.UserEntity;
import com.compass.uol.course.repositories.CustomUserDetailsService;
import com.compass.uol.course.repositories.UserRepository;
import com.compass.uol.course.services.exception.DatabaseException;
import com.compass.uol.course.services.exception.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService implements UserDetailsService, CustomUserDetailsService {

	@Autowired
	private UserRepository repository;

	public List<UserEntity> findAll() {
		return repository.findAll();
	}

	public UserEntity findById(Long id) {
		Optional<UserEntity> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public UserEntity insert(UserEntity obj) {
		return repository.save(obj);
	}

	public void delete(Long id) {
		try {
			if (!repository.existsById(id)) {
				throw new ResourceNotFoundException(id);
			}
			repository.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	public UserEntity update(Long id, UserEntity obj) {
		UserEntity entity = null;
		try {
			entity = repository.getReferenceById(id);
			updateData(entity, obj);
		}
		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
		return repository.save(entity);
	}

	private void updateData(UserEntity entity, UserEntity obj) {
		entity.setName(obj.getName());
		entity.setEmail(obj.getEmail());
		entity.setFone(obj.getFone());
	}
	
	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
		
		UserEntity user = repository.findByName(name)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		
		String[] roles = user.isAdmin() ? new String[] {"ADMIN", "USER"} : new String[] {"USER"};
		
		return User
				.builder()
				.username(user.getName())
				.password(user.getPassword())
				.roles(roles)
				.build();
	}

	@Override
	public UserDetails loadUserByEmail(String email) {
		UserEntity user = repository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		
		String[] roles = user.isAdmin() ? new String[] {"ADMIN", "USER"} : new String[] {"USER"};
		
		return User
				.builder()
				.username(user.getName())
				.password(user.getPassword())
				.roles(roles)
				.build();
	}

}
