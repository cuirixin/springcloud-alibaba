package com.phoenix.service.impl;

import com.phoenix.mapper.UserMapper;
import com.phoenix.model.User;
import com.phoenix.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * description:
 * date: 2020/5/9 7:02 下午
 * author: phoenix
 * version: 1.0.0
 */

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User getById(Integer id) {
        return userMapper.findById(id);
    }
}
