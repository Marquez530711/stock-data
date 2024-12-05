package com.ruxin.sd.source;

import com.ruxin.sd.source.entity.StockInfoDTO;

import java.util.List;

public interface BaseSource {

  /** get all A stock info */
  List<StockInfoDTO> getAllAStockInfo();

}
