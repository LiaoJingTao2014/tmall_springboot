package com.springstory.tmall.es;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.springstory.tmall.pojo.Product;

public interface ProductESDAO extends ElasticsearchRepository<Product, Integer> {

}