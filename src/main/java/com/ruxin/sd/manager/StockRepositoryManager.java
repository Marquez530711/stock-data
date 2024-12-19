package com.ruxin.sd.manager;

import com.ruxin.sd.repository.StockDailyPriceQfqRepository;
import com.ruxin.sd.repository.StockInfoRepository;
import com.ruxin.sd.repository.entity.StockDailyPriceQfqEntity;
import com.ruxin.sd.repository.entity.StockInfoEntity;
import com.ruxin.sd.source.entity.StockDetailDTO;
import com.ruxin.sd.source.entity.StockInfoDTO;
import com.ruxin.sd.source.entity.StockPriceDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class StockRepositoryManager {

    private final StockInfoRepository stockInfoRepository;
    private final StockDailyPriceQfqRepository stockDailyPriceQfqRepository;

    public StockRepositoryManager(StockInfoRepository stockInfoRepository,
                                  StockDailyPriceQfqRepository stockDailyPriceQfqRepository) {
        this.stockInfoRepository = stockInfoRepository;
        this.stockDailyPriceQfqRepository = stockDailyPriceQfqRepository;
    }

    public List<StockInfoEntity> getAllAStockInfo() {
        return stockInfoRepository.findAll();
    }

    public void createOrUpdateStockInfo(StockInfoDTO stockInfoDTO, StockDetailDTO stockDetail) {
        stockInfoRepository.findByStockCode(stockInfoDTO.getCode())
                .ifPresentOrElse(stockInfoEntity -> {
                    //compare if not equal then update
                    if (!stockInfoEntity.getStockName().equals(stockDetail.getName())
                            || stockInfoEntity.getTotalShares().compareTo(stockDetail.getTotalShares()) != 0
                            || stockInfoEntity.getOutstandingShares().compareTo(stockDetail.getOutstandingShares()) != 0
                            || stockInfoEntity.getTotalMarketValue().compareTo(stockDetail.getTotalMarketValue()) != 0
                            || stockInfoEntity.getOutstandingMarketValue().compareTo(stockDetail.getOutstandingMarketValue()) != 0
                            || !stockInfoEntity.getIndustry().equals(stockDetail.getIndustry())
                            || !stockInfoEntity.getListingDate().equals(stockDetail.getListingDate())) {
                        stockInfoEntity.update(stockDetail);
                        stockInfoRepository.save(stockInfoEntity);
                    }
                }, () -> {
                    //save new stock detail
                    StockInfoEntity stockInfoEntity = new StockInfoEntity();
                    stockInfoEntity.setStockCode(stockInfoDTO.getCode());
                    stockInfoEntity.setStockName(stockDetail.getName());
                    stockInfoEntity.setTotalShares(stockDetail.getTotalShares());
                    stockInfoEntity.setOutstandingShares(stockDetail.getOutstandingShares());
                    stockInfoEntity.setTotalMarketValue(stockDetail.getTotalMarketValue());
                    stockInfoEntity.setOutstandingMarketValue(stockDetail.getOutstandingMarketValue());
                    stockInfoEntity.setIndustry(stockDetail.getIndustry());
                    stockInfoEntity.setListingDate(stockDetail.getListingDate());
                    stockInfoRepository.save(stockInfoEntity);
                });
    }

    public void createOrUpdateStockPrice(StockInfoEntity stockInfoEntity, StockPriceDTO stockPriceDTO) {
        //if exist then return(don't need update), else save
        stockDailyPriceQfqRepository.findByStockCodeAndTradeDate(stockInfoEntity.getStockCode(),
                        stockPriceDTO.getTradeDate())
                .ifPresentOrElse(stockDailyPriceQfqEntity -> {
                    log.warn("Stock price already exist for code: {}, date: {}", stockInfoEntity.getStockCode(),
                            stockPriceDTO.getTradeDate());
                }, () -> {
                    //save new stock price
                    log.info("Save stock price for code: {}, date: {}", stockInfoEntity.getStockCode(),
                            stockPriceDTO.getTradeDate());
                    StockDailyPriceQfqEntity stockDailyPriceQfqEntity = StockDailyPriceQfqEntity.from(stockPriceDTO);
                    stockDailyPriceQfqRepository.save(stockDailyPriceQfqEntity);
                });
    }

}
