package com.ruxin.sd.service;

import com.ruxin.sd.repository.StockDailyPriceQfqRepository;
import com.ruxin.sd.repository.StockInfoRepository;
import com.ruxin.sd.repository.entity.StockDailyPriceQfqEntity;
import com.ruxin.sd.repository.entity.StockInfoEntity;
import com.ruxin.sd.source.BaseSource;
import com.ruxin.sd.source.entity.StockDetailDTO;
import com.ruxin.sd.source.entity.StockInfoDTO;
import com.ruxin.sd.source.entity.StockPriceDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
public class DataSchedulerService {

    private final BaseSource baseSource;
    private final StockInfoRepository stockInfoRepository;
    private final StockDailyPriceQfqRepository stockDailyPriceQfqRepository;

    public DataSchedulerService(@Qualifier("AKToolsSource") BaseSource baseSource,
                                StockInfoRepository stockInfoRepository,
                                StockDailyPriceQfqRepository stockDailyPriceQfqRepository) {
        this.baseSource = baseSource;
        this.stockInfoRepository = stockInfoRepository;
        this.stockDailyPriceQfqRepository = stockDailyPriceQfqRepository;
    }

    public void refreshStockInfo() {
        log.info("refresh stock info");
        List<StockInfoDTO> allAStockInfo = baseSource.getAllAStockInfo();
        if (allAStockInfo.isEmpty()) {
            log.error("Refresh stock info, Failed to get all A stock info");
            return;
        }
        for (StockInfoDTO stockInfoDTO : allAStockInfo) {
            //1.query and update stock info
            queryAndUpdateStockInfo(stockInfoDTO);
        }
        log.info("refresh stock info done");
    }

    public void refreshStockPrice(LocalDate startDate, LocalDate endDate, String adjustType) {
        log.info("refresh stock price");
        List<StockInfoDTO> allAStockInfo = baseSource.getAllAStockInfo();
        if (allAStockInfo.isEmpty()) {
            log.error("Refresh stock price, Failed to get all A stock info");
            return;
        }

        //序列化时间格式, e.g. 20210301
        String start = startDate.format(DateTimeFormatter.BASIC_ISO_DATE);
        String end = endDate.format(DateTimeFormatter.BASIC_ISO_DATE);
        for (StockInfoDTO stockInfoDTO : allAStockInfo) {
            //query today price
            List<StockPriceDTO> stockPrice = baseSource.getAStockPrice(stockInfoDTO.getCode(), start, end, adjustType);
            if (stockPrice.isEmpty()) {
                log.error("Failed to get stock price for code: {},start: {},end: {}, adjustType: {}",
                        stockInfoDTO.getCode(),
                        start,
                        end,
                        adjustType);
                continue;
            }
            //todo save stock price
            for (StockPriceDTO price : stockPrice) {
                log.info("Stock price: {}", price);
                //if exist then return(don't need update), else save
                stockDailyPriceQfqRepository.findByStockCodeAndTradeDate(stockInfoDTO.getCode(), price.getTradeDate())
                        .ifPresentOrElse(stockDailyPriceQfqEntity -> {
                            log.warn("Stock price already exist for code: {}, date: {}", stockInfoDTO.getCode(),
                                    price.getTradeDate());
                        }, () -> {
                            //save new stock price
                            log.info("Save stock price for code: {}, date: {}", stockInfoDTO.getCode(),
                                    price.getTradeDate());
                            StockDailyPriceQfqEntity stockDailyPriceQfqEntity = StockDailyPriceQfqEntity.from(price);
                            stockDailyPriceQfqRepository.save(stockDailyPriceQfqEntity);
                        });
            }
        }
    }

    private void queryAndUpdateStockInfo(StockInfoDTO stockInfoDTO) {
        StockDetailDTO stockDetail = baseSource.getAStockDetail(stockInfoDTO.getCode());
        if (stockDetail == null) {
            log.error("Failed to get stock detail for code: {}", stockInfoDTO.getCode());
            return;
        }
        log.info("Stock detail: {}", stockDetail);
        //todo async save stock info
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
}