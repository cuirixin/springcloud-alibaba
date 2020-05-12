package com.phoenix.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * description:
 * date: 2020/5/12 4:11 下午
 * author: phoenix
 * version: 1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TxLog {
    private String txId;
    private Date date;
}
