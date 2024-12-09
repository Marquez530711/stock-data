package com.ruxin.sd.repository;

import com.ruxin.sd.repository.entity.StockDailyPriceQfqEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockDailyPriceQfqRepository extends JpaRepository<StockDailyPriceQfqEntity, String> {
    Optional<StockDailyPriceQfqEntity> findByStockCodeAndTradeDate(String stockCode, String tradeDate);
}
