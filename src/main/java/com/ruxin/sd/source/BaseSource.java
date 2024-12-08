package com.ruxin.sd.source;

import com.ruxin.sd.source.entity.StockDetailDTO;
import com.ruxin.sd.source.entity.StockInfoDTO;
import com.ruxin.sd.source.entity.StockPriceDTO;

import java.util.List;

public interface BaseSource {

    /**
     * get all A stock info
     */
    List<StockInfoDTO> getAllAStockInfo();

    StockDetailDTO getAStockDetail(String code);

    List<StockPriceDTO> getAStockPrice(String code, String startDate, String endDate, String adjust);
}
