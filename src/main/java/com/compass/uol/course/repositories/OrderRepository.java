package com.compass.uol.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.compass.uol.course.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	
}
