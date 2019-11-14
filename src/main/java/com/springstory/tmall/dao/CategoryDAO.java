package com.springstory.tmall.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springstory.tmall.pojo.Category;

public interface CategoryDAO extends JpaRepository<Category,Integer>{

}
