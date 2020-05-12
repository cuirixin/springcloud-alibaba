package com.phoenix.model;

import lombok.Data;

/**
 * description:
 * date: 2020/5/9 6:01 下午
 * author: phoenix
 * version: 1.0.0
 */

@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private String mobile;

    @Override
    public String toString() {
        return "User [username=" + username + ", password=" + password + "]";
    }
}
