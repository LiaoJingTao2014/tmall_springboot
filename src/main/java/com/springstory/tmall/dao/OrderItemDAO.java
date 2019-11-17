package com.springstory.tmall.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springstory.tmall.pojo.Order;
import com.springstory.tmall.pojo.OrderItem;
import com.springstory.tmall.pojo.Product;
import com.springstory.tmall.pojo.User;

public interface OrderItemDAO extends JpaRepository<OrderItem, Integer> {

    List<OrderItem> findByOrderOrderByIdDesc(Order order);

    List<OrderItem> findByProduct(Product product);

    List<OrderItem> findByUserAndOrderIsNull(User user);

}