package com.springstory.tmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springstory.tmall.dao.PropertyDAO;
import com.springstory.tmall.pojo.Category;
import com.springstory.tmall.pojo.Property;
import com.springstory.tmall.util.Page4Navigator;

@Service
public class PropertyService {

    @Autowired
    PropertyDAO propertyDao;

    @Autowired
    CategoryService categoryService;

    public void add(Property property) {
        propertyDao.save(property);
    }

    public void delete(int id) {
        propertyDao.deleteById(id);
    }

    public void update(Property property) {
        propertyDao.save(property);
    }

    public Property get(int id) {
        return propertyDao.getOne(id);
    }

    public Page4Navigator<Property> list(int cid, int start, int size, int navigatePages) {
        Category category = categoryService.get(cid);
        if (category == null) {
            return null;
        }
        Sort sort = Sort.by(new Sort.Order(Sort.Direction.DESC, "id"));
        Pageable pageable = PageRequest.of(start, size, sort);
        Page<Property> pageFromJPA = propertyDao.findByCategory(category, pageable);

        return new Page4Navigator<>(pageFromJPA, navigatePages);
    }

    public List<Property> listByCategory(Category category) {
        return propertyDao.findByCategory(category);
    }
}
