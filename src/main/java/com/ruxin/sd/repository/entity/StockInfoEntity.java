package com.ruxin.sd.repository.entity;

import jakarta.persistence.Entity;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.Date;

/**
 * CREATE TABLE stock_info (
 * stock_code VARCHAR(10) NOT NULL PRIMARY KEY COMMENT '股票代码',
 * stock_name VARCHAR(50) NOT NULL COMMENT '股票简称',
 * total_shares BIGINT NOT NULL COMMENT '总股本，单位：股',
 * outstanding_shares BIGINT NOT NULL COMMENT '流通股，单位：股',
 * total_market_value DECIMAL(20, 2) NOT NULL COMMENT '总市值，单位：元',
 * outstanding_market_value DECIMAL(20, 2) NOT NULL COMMENT '流通市值，单位：元',
 * industry VARCHAR(50) NOT NULL COMMENT '所属行业',
 * listing_date DATE NOT NULL COMMENT '上市时间'
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='股票基本信息表';
 */
@Data
@Entity(name = "stock_info")
public class StockInfoEntity {

    @Id
    private String stockCode;

    private String stockName;

    private BigDecimal totalShares;

    private BigDecimal outstandingShares;

    private BigDecimal totalMarketValue;

    private BigDecimal outstandingMarketValue;

    private String industry;

    private String listingDate;
}
