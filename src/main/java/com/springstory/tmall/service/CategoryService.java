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

import com.springstory.tmall.dao.CategoryDAO;
import com.springstory.tmall.pojo.Category;
import com.springstory.tmall.pojo.Product;
import com.springstory.tmall.util.Page4Navigator;

@Service
@CacheConfig(cacheNames = "categories")
public class CategoryService {
    @Autowired
    CategoryDAO categoryDao;

    @Cacheable(key = "'categories-all'")
    public List<Category> list() {
        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "id");
        return categoryDao.findAll(Sort.by(order));
    }

    @Cacheable(key = "'categories-page-'+#p0+ '-' + #p1")
    public Page4Navigator<Category> list(int start, int size, int navigatePages) {
        Sort.Order order = new Sort.Order(Sort.Direction.DESC, "id");
        Sort sort = Sort.by(order);
        Pageable pageable = PageRequest.of(start, size, sort);
        Page<Category> pageFromJPA = categoryDao.findAll(pageable);

        return new Page4Navigator<>(pageFromJPA, navigatePages);
    }

    @CacheEvict(allEntries = true)
    public void add(Category bean) {
        categoryDao.save(bean);
    }

    @CacheEvict(allEntries = true)
    public void delete(int id) {
        categoryDao.deleteById(id);
    }

    @Cacheable(key = "'categories-one-'+ #p0")
    public Category get(int id) {
        return categoryDao.getOne(id);
    }

    @CacheEvict(allEntries = true)
    public void update(Category bean) {
        categoryDao.save(bean);
    }

    public void removeCategoryFromProduct(List<Category> cs) {
        for (Category category : cs) {
            removeCategoryFromProduct(category);
        }
    }

    public void removeCategoryFromProduct(Category category) {
        List<Product> products = category.getProducts();
        if (null != products) {
            for (Product product : products) {
                product.setCategory(null);
            }
        }

        List<List<Product>> productsByRow = category.getProductsByRow();
        if (null != productsByRow) {
            for (List<Product> ps : productsByRow) {
                for (Product p : ps) {
                    p.setCategory(null);
                }
            }
        }
    }
}
