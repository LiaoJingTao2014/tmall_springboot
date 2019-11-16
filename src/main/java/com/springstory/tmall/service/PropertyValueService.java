package com.springstory.tmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springstory.tmall.dao.PropertyValueDAO;
import com.springstory.tmall.pojo.Product;
import com.springstory.tmall.pojo.Property;
import com.springstory.tmall.pojo.PropertyValue;

@Service
public class PropertyValueService {

    @Autowired
    PropertyValueDAO propertyValueDao;

    @Autowired
    PropertyService propertyService;

    public void update(PropertyValue bean) {
        propertyValueDao.save(bean);
    }

    public void init(Product product) {
        List<Property> propertys = propertyService.listByCategory(product.getCategory());
        for (Property property : propertys) {
            PropertyValue propertyValue = getByPropertyAndProduct(product, property);
            if (null == propertyValue) {
                propertyValue = new PropertyValue();
                propertyValue.setProduct(product);
                propertyValue.setProperty(property);
                propertyValueDao.save(propertyValue);
            }
        }
    }

    public PropertyValue getByPropertyAndProduct(Product product, Property property) {
        return propertyValueDao.getByPropertyAndProduct(property, product);
    }

    public List<PropertyValue> list(Product product) {
        return propertyValueDao.findByProductOrderByIdDesc(product);
    }

}