package com.springstory.tmall.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springstory.tmall.pojo.Order;
import com.springstory.tmall.pojo.User;

public interface OrderDAO extends JpaRepository<Order, Integer> {

    List<Order> findByUserAndStatusNotOrderByIdDesc(User user, String status);

}