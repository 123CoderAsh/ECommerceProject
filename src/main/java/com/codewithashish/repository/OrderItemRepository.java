package com.codewithashish.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codewithashish.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{

}
