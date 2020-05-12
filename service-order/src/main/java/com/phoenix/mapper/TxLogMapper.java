package com.phoenix.mapper;

import com.phoenix.model.Order;
import com.phoenix.model.TxLog;
import org.springframework.stereotype.Repository;

/**
 * description:
 * date: 2020/5/9 6:05 下午
 * author: phoenix
 * version: 1.0.0
 */
@Repository
public interface TxLogMapper {
    TxLog getByTxId(String txId);
    Long createLog(TxLog txLog);
}
