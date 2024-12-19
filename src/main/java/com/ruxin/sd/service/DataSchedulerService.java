package com.ruxin.sd.service;

import com.ruxin.sd.manager.StockRepositoryManager;
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
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class DataSchedulerService {

    private final BaseSource baseSource;
    private final StockRepositoryManager stockRepositoryManager;
    private final ThreadPoolExecutor threadPoolExecutor;

    public DataSchedulerService(@Qualifier("AKToolsSource") BaseSource baseSource,
                                StockRepositoryManager stockRepositoryManager) {
        this.baseSource = baseSource;
        this.stockRepositoryManager = stockRepositoryManager;
        this.threadPoolExecutor = new ThreadPoolExecutor(5, 10, 60, TimeUnit.SECONDS,
                new java.util.concurrent.LinkedBlockingQueue<>());

    }

    public void refreshStockInfo() {
        log.info("refresh stock info");
        List<StockInfoDTO> allAStockInfo = baseSource.getAllAStockInfo();
        if (allAStockInfo.isEmpty()) {
            log.error("Refresh stock info, Failed to get all A stock info");
            return;
        }
        log.info("All A stock info size:{}", allAStockInfo.size());
        for (StockInfoDTO stockInfoDTO : allAStockInfo) {
            //1.query and update stock info
            queryAndUpdateStockInfo(stockInfoDTO);
        }
        log.info("refresh stock info done");
    }

    public void refreshStockPrice(LocalDate startDate, LocalDate endDate, String adjustType) {
        log.info("refresh stock price");
        List<StockInfoEntity> allAStockInfo = stockRepositoryManager.getAllAStockInfo();
        if (allAStockInfo.isEmpty()) {
            log.error("Refresh stock price, Failed to get all A stock info");
            return;
        }

        //序列化时间格式, e.g. 20210301
        String start = startDate.format(DateTimeFormatter.BASIC_ISO_DATE);
        String end = endDate.format(DateTimeFormatter.BASIC_ISO_DATE);
        for (StockInfoEntity stockInfoDTO : allAStockInfo) {
            //query today price
            List<StockPriceDTO> stockPrice = baseSource.getAStockPrice(stockInfoDTO.getStockCode(), start, end,
                    adjustType);
            if (stockPrice.isEmpty()) {
                log.error("Failed to get stock price for code: {},start: {},end: {}, adjustType: {}",
                        stockInfoDTO.getStockCode(),
                        start,
                        end,
                        adjustType);
                continue;
            }
            //todo save stock price
            for (StockPriceDTO price : stockPrice) {
                log.info("Stock price: {}", price);
                threadPoolExecutor.execute(() -> stockRepositoryManager.createOrUpdateStockPrice(stockInfoDTO, price));
            }
        }
        log.info("refresh stock price done");
    }

    private void queryAndUpdateStockInfo(StockInfoDTO stockInfoDTO) {
        StockDetailDTO stockDetail = baseSource.getAStockDetail(stockInfoDTO.getCode());
        if (stockDetail == null) {
            log.error("Failed to get stock detail for code: {}", stockInfoDTO.getCode());
            return;
        }
        log.info("Stock detail: {}", stockDetail);
        threadPoolExecutor.execute(() -> stockRepositoryManager.createOrUpdateStockInfo(stockInfoDTO, stockDetail));
    }
}