package com.springstory.tmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springstory.tmall.dao.ProductDAO;
import com.springstory.tmall.pojo.Category;
import com.springstory.tmall.pojo.Product;
import com.springstory.tmall.util.Page4Navigator;

@Service
public class ProductService {

    @Autowired
    ProductDAO productDao;

    @Autowired
    CategoryService categoryService;

    public void add(Product bean) {
        productDao.save(bean);
    }

    public void delete(int id) {
        productDao.deleteById(id);
    }

    public Product get(int id) {
        return productDao.getOne(id);
    }

    public void update(Product bean) {
        productDao.save(bean);
    }

    public Page4Navigator<Product> list(int cid, int start, int size, int navigatePages) {
        Category category = categoryService.get(cid);
        Sort sort = Sort.by(new Sort.Order(Sort.Direction.DESC, "id"));
        Pageable pageable = PageRequest.of(start, size, sort);
        Page<Product> pageFromJPA = productDao.findByCategory(category, pageable);

        return new Page4Navigator<>(pageFromJPA, navigatePages);
    }
}
