package com.ruxin.sd.repository;

import com.ruxin.sd.repository.entity.StockInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockInfoRepository extends JpaRepository<StockInfoEntity, String> {

    Optional<StockInfoEntity> findByStockCode(String stockCode);
}
