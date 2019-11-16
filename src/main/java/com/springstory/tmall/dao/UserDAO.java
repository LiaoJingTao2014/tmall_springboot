package com.springstory.tmall.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springstory.tmall.pojo.User;

public interface UserDAO extends JpaRepository<User, Integer> {

}