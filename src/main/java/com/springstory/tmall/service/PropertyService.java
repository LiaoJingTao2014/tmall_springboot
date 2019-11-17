package com.springstory.tmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
@CacheConfig(cacheNames = "properties")
public class PropertyService {

    @Autowired
    PropertyDAO propertyDao;

    @Autowired
    CategoryService categoryService;

    @CacheEvict(allEntries = true)
    public void add(Property property) {
        propertyDao.save(property);
    }

    @CacheEvict(allEntries = true)
    public void delete(int id) {
        propertyDao.deleteById(id);
    }

    @CacheEvict(allEntries = true)
    public void update(Property property) {
        propertyDao.save(property);
    }

    @Cacheable(key = "'properties-one-' + #p0")
    public Property get(int id) {
        return propertyDao.getOne(id);
    }

    @Cacheable(key = "'properties-cid-'+#p0+'-page-'+#p1 + '-' + #p2 ")
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

    @Cacheable(key = "'properties-cid-'+ #p0.id")
    public List<Property> listByCategory(Category category) {
        return propertyDao.findByCategory(category);
    }
}
