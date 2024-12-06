package com.ruxin.sd.source.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

/**
 * { "日期": "2024-12-02T00:00:00.000", "股票代码": "600153", "开盘": 9.78, "收盘": 10.03, "最高": 10.15, "最低":
 * 9.76, "成交量": 432779, "成交额": 431017183, "振幅": 3.98, "涨跌幅": 2.35, "涨跌额": 0.23, "换手率": 1.49 }
 */
@Data
public class StockPriceDTO {
  @JsonAlias("日期")
  private String tradeDate;

  @JsonAlias("股票代码")
  private String stockCode;

  @JsonAlias("开盘")
  private double openPrice;

  @JsonAlias("收盘")
  private double closePrice;

  @JsonAlias("最高")
  private double highPrice;

  @JsonAlias("最低")
  private double lowPrice;

  @JsonAlias("成交量")
  private long volume;

  @JsonAlias("成交额")
  private long turnover;

  @JsonAlias("振幅")
  private double amplitude;

  @JsonAlias("涨跌幅")
  private double changePercentage;

  @JsonAlias("涨跌额")
  private double changeAmount;

  @JsonAlias("换手率")
  private double turnoverRate;
}
