package com.springstory.tmall.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springstory.tmall.pojo.Category;
import com.springstory.tmall.service.CategoryService;

@RestController
public class CategoryController {
    @Autowired CategoryService categoryService;
     
    @GetMapping("/categories")
    public List<Category> list() throws Exception {
        return categoryService.list();
    }
}