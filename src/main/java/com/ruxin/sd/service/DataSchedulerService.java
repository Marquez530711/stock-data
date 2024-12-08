package com.ruxin.sd.service;

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

    public DataSchedulerService(@Qualifier("AKToolsSource") BaseSource baseSource) {
        this.baseSource = baseSource;
    }

    public void refreshStockInfo() {
        log.info("refresh stock info");
        List<StockInfoDTO> allAStockInfo = baseSource.getAllAStockInfo();
        if (allAStockInfo.isEmpty()) {
            log.error("Failed to get all A stock info");
            return;
        }
        //序列化时间 20210301
        String date = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        for (StockInfoDTO stockInfoDTO : allAStockInfo) {
            StockDetailDTO stockDetail = baseSource.getAStockDetail(stockInfoDTO.getCode());
            if (stockDetail == null) {
                log.error("Failed to get stock detail for code: {}", stockInfoDTO.getCode());
                continue;
            }
            log.info("Stock detail: {}", stockDetail);
            //todo save stock detail

            //query today price
            List<StockPriceDTO> stockPrice = baseSource.getAStockPrice(stockInfoDTO.getCode(), date, date, "qfq");
            if (stockPrice.isEmpty()) {
                log.error("Failed to get stock price for code: {}", stockInfoDTO.getCode());
                continue;
            }
            //todo save stock price
            StockPriceDTO price = stockPrice.iterator().next();
            log.info("Stock price: {}", price);

        }


    }
}
