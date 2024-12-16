package com.ruxin.sd.repository.entity;

import com.ruxin.sd.source.entity.StockDetailDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;


import java.math.BigDecimal;
import java.util.Objects;

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

    public void update(StockDetailDTO stockDetailDTO) {
        this.stockName = stockDetailDTO.getName();
        this.totalShares = stockDetailDTO.getTotalShares();
        this.outstandingShares = stockDetailDTO.getOutstandingShares();
        this.totalMarketValue = stockDetailDTO.getTotalMarketValue();
        this.outstandingMarketValue = stockDetailDTO.getOutstandingMarketValue();
        this.industry = stockDetailDTO.getIndustry();
        this.listingDate = stockDetailDTO.getListingDate();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        StockInfoEntity that = (StockInfoEntity) o;
        return stockCode.equals(that.stockCode)
                && stockName.equals(that.stockName)
                && totalShares.compareTo(that.totalShares) == 0
                && outstandingShares.compareTo(that.outstandingShares) == 0
                && totalMarketValue.compareTo(that.totalMarketValue) == 0
                && outstandingMarketValue.compareTo(that.outstandingMarketValue) == 0
                && industry.equals(that.industry)
                && listingDate.equals(that.listingDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stockCode, stockName, totalShares, outstandingShares, totalMarketValue,
                outstandingMarketValue, industry, listingDate);
    }
}
