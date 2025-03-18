package com.compass.uol.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.compass.uol.course.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
}
