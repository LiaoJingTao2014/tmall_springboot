package com.springstory.tmall.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springstory.tmall.pojo.Order;
import com.springstory.tmall.pojo.OrderItem;

public interface OrderItemDAO extends JpaRepository<OrderItem, Integer> {

    List<OrderItem> findByOrderOrderByIdDesc(Order order);

}