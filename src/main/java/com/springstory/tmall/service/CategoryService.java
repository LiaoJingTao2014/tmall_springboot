package com.springstory.tmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springstory.tmall.dao.CategoryDAO;
import com.springstory.tmall.pojo.Category;

@Service
public class CategoryService {
    @Autowired CategoryDAO categoryDAO;

    public List<Category> list() {
        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "id");
        return categoryDAO.findAll(Sort.by(order));
    }
}
