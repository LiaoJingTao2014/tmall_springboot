package com.springstory.tmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springstory.tmall.dao.ProductImageDAO;
import com.springstory.tmall.pojo.Product;
import com.springstory.tmall.pojo.ProductImage;

@Service
public class ProductImageService {

    public static final String type_single = "single";
    public static final String type_detail = "detail";

    @Autowired
    ProductImageDAO productImageDao;
    @Autowired
    ProductService productService;

    public void add(ProductImage bean) {
        productImageDao.save(bean);

    }

    public void delete(int id) {
        productImageDao.deleteById(id);
    }

    public ProductImage get(int id) {
        return productImageDao.getOne(id);
    }

    public List<ProductImage> listSingleProductImages(Product product) {
        return productImageDao.findByProductAndTypeOrderByIdDesc(product, type_single);
    }

    public List<ProductImage> listDetailProductImages(Product product) {
        return productImageDao.findByProductAndTypeOrderByIdDesc(product, type_detail);
    }

    public void setFirstProdutImage(Product product) {
        List<ProductImage> singleImages = listSingleProductImages(product);
        if (!singleImages.isEmpty())
            product.setFirstProductImage(singleImages.get(0));
        else
            product.setFirstProductImage(new ProductImage());

    }

    public void setFirstProdutImages(List<Product> products) {
        for (Product product : products)
            setFirstProdutImage(product);
    }

}