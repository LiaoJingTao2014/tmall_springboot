package com.springstory.tmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springstory.tmall.dao.CategoryDAO;
import com.springstory.tmall.pojo.Category;
import com.springstory.tmall.util.Page4Navigator;

@Service
public class CategoryService {
    @Autowired
    CategoryDAO categoryDao;

    public List<Category> list() {
        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "id");
        return categoryDao.findAll(Sort.by(order));
    }

    public Page4Navigator<Category> list(int start, int size, int navigatePages) {
        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "id");
        Sort sort = Sort.by(order);
        Pageable pageable = PageRequest.of(start, size, sort);
        Page<Category> pageFromJPA = categoryDao.findAll(pageable);

        return new Page4Navigator<>(pageFromJPA, navigatePages);
    }

    public void add(Category bean) {
        categoryDao.save(bean);
    }

    public void delete(int id) {
        categoryDao.deleteById(id);
    }

    public Category get(int id) {
        return categoryDao.getOne(id);
    }

    public void update(Category bean) {
        categoryDao.save(bean);
    }
}
