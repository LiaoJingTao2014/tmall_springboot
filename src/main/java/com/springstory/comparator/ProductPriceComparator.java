package com.springstory.comparator;

import java.util.Comparator;

import com.springstory.tmall.pojo.Product;

public class ProductPriceComparator implements Comparator<Product> {

    @Override
    public int compare(Product p1, Product p2) {
        return (int) (p1.getPromotePrice() - p2.getPromotePrice());
    }

}
