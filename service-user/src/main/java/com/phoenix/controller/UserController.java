package com.phoenix.controller;

import com.phoenix.model.User;
import com.phoenix.service.UserService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * description:
 * date: 2020/5/9 7:01 下午
 * author: phoenix
 * version: 1.0.0
 */

@RestController
public class UserController {

    @Resource
    private UserService userService;

    @RequestMapping("/user/{id}")
    public User user(@PathVariable Integer id) {
        User user = userService.getById(id);
        return user;
    }
}
