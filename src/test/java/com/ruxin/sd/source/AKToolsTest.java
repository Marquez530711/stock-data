package com.ruxin.sd.source;

import com.ruxin.sd.source.entity.StockDetailDTO;
import com.ruxin.sd.source.entity.StockInfoDTO;
import com.ruxin.sd.source.entity.StockPriceDTO;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AKToolsTest {

    private final AKToolsSource akToolsSource = new AKToolsSource();

    @Test
    public void testGetAllAStockInfo() {
        List<StockInfoDTO> allAStockInfo = akToolsSource.getAllAStockInfo();
        assertFalse(allAStockInfo.isEmpty());
    }

    @Test
    public void testGetAStockDetail() {
        StockDetailDTO aStockDetail = akToolsSource.getAStockDetail("600153");
        assertNotNull(aStockDetail);
    }

    @Test
    public void testGetAStockPrice() {
        List<StockPriceDTO> aStockPrice =
                akToolsSource.getAStockPrice("600153", "20241202", "20241203", "qfq");
        assertEquals(2, aStockPrice.size());
    }
}
