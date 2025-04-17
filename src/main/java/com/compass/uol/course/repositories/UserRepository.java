package com.compass.uol.course.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.compass.uol.course.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	
	Optional<UserEntity> findByEmail(String email);
	Optional<UserEntity> findByName(String name);

	
}
