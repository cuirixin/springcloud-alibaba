package com.phoenix.mapper;

import com.phoenix.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * description:
 * date: 2020/5/9 6:05 下午
 * author: phoenix
 * version: 1.0.0
 */
@Repository
public interface UserMapper {
    public List<User> findAll();
    public User findById(Integer id);
    public Integer insertUser(User user) throws Exception;
    public void updateUser(User user);
    public void deleteById(Integer id);
}
