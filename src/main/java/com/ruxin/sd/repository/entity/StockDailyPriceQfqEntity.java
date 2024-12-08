package com.ruxin.sd.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.math.BigDecimal;

/**
 * CREATE TABLE stock_daily_data_qfq (
 * id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '主键',
 * trade_date DATE NOT NULL COMMENT '交易日期',
 * stock_code VARCHAR(10) NOT NULL COMMENT '股票代码',
 * open_price DECIMAL(10, 2) NOT NULL COMMENT '开盘价',
 * close_price DECIMAL(10, 2) NOT NULL COMMENT '收盘价',
 * high_price DECIMAL(10, 2) NOT NULL COMMENT '最高价',
 * low_price DECIMAL(10, 2) NOT NULL COMMENT '最低价',
 * volume BIGINT NOT NULL COMMENT '成交量，单位：股',
 * turnover DECIMAL(20, 2) NOT NULL COMMENT '成交额，单位：元',
 * amplitude DECIMAL(5, 2) NOT NULL COMMENT '振幅，单位：百分比',
 * change_percentage DECIMAL(5, 2) NOT NULL COMMENT '涨跌幅，单位：百分比',
 * change_amount DECIMAL(10, 2) NOT NULL COMMENT '涨跌额，单位：元',
 * turnover_rate DECIMAL(5, 2) NOT NULL COMMENT '换手率，单位：百分比',
 * PRIMARY KEY (stock_code, trade_date)
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='股票每日交易数据表（前复权）';
 */
@Data
@Entity(name = "stock_daily_data_qfq")
public class StockDailyPriceQfqEntity {

    @Id
    private Long id;

    private String tradeDate;

    private String stockCode;

    private BigDecimal openPrice;

    private BigDecimal closePrice;

    private BigDecimal highPrice;

    private BigDecimal lowPrice;

    private BigDecimal volume;

    private BigDecimal turnover;

    private BigDecimal amplitude;

    private BigDecimal changePercentage;

    private BigDecimal changeAmount;

    private BigDecimal turnoverRate;
}
