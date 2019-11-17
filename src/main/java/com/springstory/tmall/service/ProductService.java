package com.springstory.tmall.service;

import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import com.springstory.tmall.dao.ProductDAO;
import com.springstory.tmall.es.ProductESDAO;
import com.springstory.tmall.pojo.Category;
import com.springstory.tmall.pojo.Product;
import com.springstory.tmall.util.Page4Navigator;

@Service
@CacheConfig(cacheNames = "products")
public class ProductService {

    @Autowired
    ProductDAO productDao;

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductImageService productImageService;

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    ReviewService reviewService;

    @Autowired
    ProductESDAO productESDAO;

    @CacheEvict(allEntries = true)
    public void add(Product bean) {
        productDao.save(bean);
        productESDAO.save(bean);
    }

    @CacheEvict(allEntries = true)
    public void delete(int id) {
        productDao.deleteById(id);
        productESDAO.deleteById(id);
    }

    @Cacheable(key = "'products-one-'+ #p0")
    public Product get(int id) {
        return productDao.getOne(id);
    }

    @CacheEvict(allEntries = true)
    public void update(Product bean) {
        productDao.save(bean);
        productESDAO.save(bean);
    }

    @Cacheable(key = "'products-cid-'+#p0+'-page-'+#p1 + '-' + #p2 ")
    public Page4Navigator<Product> list(int cid, int start, int size, int navigatePages) {
        Category category = categoryService.get(cid);
        Sort sort = Sort.by(new Sort.Order(Sort.Direction.DESC, "id"));
        Pageable pageable = PageRequest.of(start, size, sort);
        Page<Product> pageFromJPA = productDao.findByCategory(category, pageable);

        return new Page4Navigator<>(pageFromJPA, navigatePages);
    }

    public void fill(List<Category> categorys) {
        for (Category category : categorys) {
            fill(category);
        }
    }

    public void fill(Category category) {
        List<Product> products = listByCategory(category);
        productImageService.setFirstProdutImages(products);
        category.setProducts(products);
    }

    public void fillByRow(List<Category> categorys) {
        int productNumberEachRow = 8;
        for (Category category : categorys) {
            List<Product> products = category.getProducts();
            List<List<Product>> productsByRow = new ArrayList<>();
            for (int i = 0; i < products.size(); i += productNumberEachRow) {
                int size = i + productNumberEachRow;
                size = size > products.size() ? products.size() : size;
                List<Product> productsOfEachRow = products.subList(i, size);
                productsByRow.add(productsOfEachRow);
            }
            category.setProductsByRow(productsByRow);
        }
    }

    @Cacheable(key = "'products-cid-'+ #p0.id")
    public List<Product> listByCategory(Category category) {
        return productDao.findByCategoryOrderById(category);
    }

    public void setSaleAndReviewNumber(Product product) {
        int saleCount = orderItemService.getSaleCount(product);
        product.setSaleCount(saleCount);

        int reviewCount = reviewService.getCount(product);
        product.setReviewCount(reviewCount);

    }

    public void setSaleAndReviewNumber(List<Product> products) {
        for (Product product : products)
            setSaleAndReviewNumber(product);
    }

    public List<Product> search(String keyword, int start, int size) {
        initDatabase2ES();
        Sort sort = Sort.by(new Sort.Order(Sort.Direction.DESC, "id"));
        Pageable pageable = PageRequest.of(start, size, sort);

        QueryStringQueryBuilder builder = new QueryStringQueryBuilder(keyword);
        SearchQuery searchQuery = new NativeSearchQueryBuilder().withPageable(pageable).withQuery(builder).build();
        Page<Product> page = productESDAO.search(searchQuery);

        return page.getContent();
    }

    private void initDatabase2ES() {
        Pageable pageable = PageRequest.of(0, 5);
        Page<Product> page = productESDAO.findAll(pageable);
        if (page.getContent().isEmpty()) {
            List<Product> products = productDao.findAll();
            for (Product product : products) {
                productESDAO.save(product);
            }
        }
    }
}
