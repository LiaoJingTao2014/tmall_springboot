package com.springstory.tmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springstory.tmall.dao.UserDAO;
import com.springstory.tmall.pojo.User;
import com.springstory.tmall.util.Page4Navigator;

@Service
public class UserService {

    @Autowired
    UserDAO userDao;

    public Page4Navigator<User> list(int start, int size, int navigatePages) {
        Sort sort = Sort.by(new Sort.Order(Sort.Direction.DESC, "id"));
        Pageable pageable = PageRequest.of(start, size, sort);
        Page<User> pageFromJPA = userDao.findAll(pageable);

        return new Page4Navigator<>(pageFromJPA, navigatePages);
    }

}