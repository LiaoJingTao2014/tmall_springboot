package com.springstory.tmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springstory.tmall.dao.UserDAO;
import com.springstory.tmall.pojo.User;
import com.springstory.tmall.util.Page4Navigator;

@Service
@CacheConfig(cacheNames = "users")
public class UserService {

    @Autowired
    UserDAO userDao;

    @Cacheable(key = "'users-page-'+#p0+ '-' + #p1")
    public Page4Navigator<User> list(int start, int size, int navigatePages) {
        Sort sort = Sort.by(new Sort.Order(Sort.Direction.DESC, "id"));
        Pageable pageable = PageRequest.of(start, size, sort);
        Page<User> pageFromJPA = userDao.findAll(pageable);

        return new Page4Navigator<>(pageFromJPA, navigatePages);
    }

    public boolean isExist(String name) {
        User user = getByName(name);
        return null != user;
    }

    @Cacheable(key = "'users-one-name-'+ #p0")
    public User getByName(String name) {
        return userDao.findByName(name);
    }

    @CacheEvict(allEntries = true)
    public void add(User user) {
        userDao.save(user);
    }

    @Cacheable(key = "'users-one-name-'+ #p0 +'-password-'+ #p1")
    public User get(String name, String password) {
        return userDao.getByNameAndPassword(name, password);
    }

}