package com.phoenix.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * description:
 * date: 2020/5/11 10:47 上午
 * author: phoenix
 * version: 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleResponseData {
    private int code;
    private String msg;
    public Object data;

    public SimpleResponseData(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
