package com.springstory.tmall.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springstory.tmall.pojo.Product;
import com.springstory.tmall.pojo.Property;
import com.springstory.tmall.pojo.PropertyValue;

public interface PropertyValueDAO extends JpaRepository<PropertyValue, Integer> {

    List<PropertyValue> findByProductOrderByIdDesc(Product product);

    PropertyValue getByPropertyAndProduct(Property property, Product product);

}