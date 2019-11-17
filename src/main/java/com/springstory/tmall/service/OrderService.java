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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.springstory.tmall.dao.OrderDAO;
import com.springstory.tmall.pojo.Order;
import com.springstory.tmall.pojo.OrderItem;
import com.springstory.tmall.pojo.User;
import com.springstory.tmall.util.Page4Navigator;

@Service
@CacheConfig(cacheNames = "orders")
public class OrderService {
    public static final String waitPay = "waitPay";
    public static final String waitDelivery = "waitDelivery";
    public static final String waitConfirm = "waitConfirm";
    public static final String waitReview = "waitReview";
    public static final String finish = "finish";
    public static final String delete = "delete";

    @Autowired
    OrderDAO orderDAO;

    @Autowired
    OrderItemService orderItemService;

    @Cacheable(key = "'orders-page-'+#p0+ '-' + #p1")
    public Page4Navigator<Order> list(int start, int size, int navigatePages) {
        Sort sort = Sort.by(new Sort.Order(Sort.Direction.DESC, "id"));
        Pageable pageable = PageRequest.of(start, size, sort);
        Page<Order> pageFromJPA = orderDAO.findAll(pageable);

        return new Page4Navigator<>(pageFromJPA, navigatePages);
    }

    public void removeOrderFromOrderItem(List<Order> orders) {
        for (Order order : orders) {
            removeOrderFromOrderItem(order);
        }
    }

    public void removeOrderFromOrderItem(Order order) {
        List<OrderItem> orderItems = order.getOrderItems();
        for (OrderItem orderItem : orderItems) {
            orderItem.setOrder(null);
        }
    }

    @Cacheable(key = "'orders-one-'+ #p0")
    public Order get(int oid) {
        return orderDAO.getOne(oid);
    }

    @CacheEvict(allEntries = true)
    public void update(Order bean) {
        orderDAO.save(bean);
    }

    @CacheEvict(allEntries = true)
    @Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = "Exception")
    public float add(Order order, List<OrderItem> ois) {
        float total = 0;
        add(order);

        for (OrderItem oi : ois) {
            oi.setOrder(order);
            orderItemService.update(oi);
            total += oi.getProduct().getPromotePrice() * oi.getNumber();
        }
        return total;
    }

    @CacheEvict(allEntries = true)
    public void add(Order order) {
        orderDAO.save(order);
    }

    @Cacheable(key = "'orders-uid-'+ #p0.id")
    public List<Order> listByUserWithoutDelete(User user) {
        List<Order> orders = listByUserAndNotDeleted(user);
        orderItemService.fill(orders);
        return orders;
    }

    public List<Order> listByUserAndNotDeleted(User user) {
        return orderDAO.findByUserAndStatusNotOrderByIdDesc(user, OrderService.delete);
    }

    public void cacl(Order o) {
        List<OrderItem> orderItems = o.getOrderItems();
        float total = 0;
        for (OrderItem orderItem : orderItems) {
            total += orderItem.getProduct().getPromotePrice() * orderItem.getNumber();
        }
        o.setTotal(total);
    }
}