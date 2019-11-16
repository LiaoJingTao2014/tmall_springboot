package com.springstory.tmall.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springstory.tmall.dao.OrderDAO;
import com.springstory.tmall.pojo.Order;
import com.springstory.tmall.pojo.OrderItem;
import com.springstory.tmall.util.Page4Navigator;

@Service
public class OrderService {
    public static final String waitPay = "waitPay";
    public static final String waitDelivery = "waitDelivery";
    public static final String waitConfirm = "waitConfirm";
    public static final String waitReview = "waitReview";
    public static final String finish = "finish";
    public static final String delete = "delete";

    @Autowired
    OrderDAO orderDAO;

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

    private void removeOrderFromOrderItem(Order order) {
        List<OrderItem> orderItems = order.getOrderItems();
        for (OrderItem orderItem : orderItems) {
            orderItem.setOrder(null);
        }
    }

    public Order get(int oid) {
        return orderDAO.getOne(oid);
    }

    public void update(Order bean) {
        orderDAO.save(bean);
    }

}