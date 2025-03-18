package com.compass.uol.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.compass.uol.course.entities.OrderItem;
import com.compass.uol.course.entities.pk.OrderItemPK;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {
	
}
