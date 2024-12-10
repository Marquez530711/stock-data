package com.ruxin.sd.controller;

import com.ruxin.sd.controller.entity.RefreshStockPriceRequest;
import com.ruxin.sd.service.DataSchedulerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataSchedulerController {

    private final DataSchedulerService dataSchedulerService;


    public DataSchedulerController(DataSchedulerService dataSchedulerService) {
        this.dataSchedulerService = dataSchedulerService;
    }

    @PostMapping("/refreshStockInfo")
    public void refreshStockInfo() {
        dataSchedulerService.refreshStockInfo();
    }

    @PostMapping("/refreshStockPrice")
    public void refreshStockPrice(@RequestBody RefreshStockPriceRequest request) {
        dataSchedulerService.refreshStockPrice(request.getStartDate(), request.getEndDate(), request.getAdjustType());
    }
}
