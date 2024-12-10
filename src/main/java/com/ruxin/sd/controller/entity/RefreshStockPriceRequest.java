package com.ruxin.sd.controller.entity;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RefreshStockPriceRequest {
    private LocalDate startDate;
    private LocalDate endDate;
    private String adjustType;
}
