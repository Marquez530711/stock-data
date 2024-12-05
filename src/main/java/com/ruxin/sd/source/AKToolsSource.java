package com.ruxin.sd.source;

import com.ruxin.sd.source.entity.StockInfoDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AKToolsSource implements BaseSource{

    @Override
    public List<StockInfoDTO> getAllAStockInfo() {
        return List.of();
    }
}
