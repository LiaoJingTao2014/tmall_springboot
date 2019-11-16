package com.springstory.tmall.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springstory.tmall.pojo.Order;

public interface OrderDAO extends JpaRepository<Order, Integer> {
}