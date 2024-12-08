package com.ruxin.sd.source.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class StockDetailDTO {

    private BigDecimal totalMarketValue;
    private BigDecimal outstandingMarketValue;
    private String industry;
    private String listingDate;
    private String code;
    private String name;
    private BigDecimal totalShares;
    private BigDecimal outstandingShares;


    /*[
      {
    "item": "总市值",
    "value": 226273006188.68
     },
    {
    "item": "流通市值",
    "value": 226269500376.48
      },
     {
    "item": "行业",
    "value": "银行"
      },
     {
    "item": "上市时间",
    "value": 19910403
     },
     {
    "item": "股票代码",
    "value": "000001"
     },
    {
    "item": "股票简称",
    "value": "平安银行"
     },
    {
    "item": "总股本",
    "value": 19405918198
    },
    {
    "item": "流通股",
    "value": 19405617528
        }
    ]
     */
    public static StockDetailDTO from(List<AKApiResponse> akApiResponses) {
        StockDetailDTO stockDetailDTO = new StockDetailDTO();
        for (AKApiResponse akApiResponse : akApiResponses) {
            switch (akApiResponse.getItem()) {
                case "总市值":
                    stockDetailDTO.setTotalMarketValue(new BigDecimal(akApiResponse.getValue()));
                    break;
                case "流通市值":
                    stockDetailDTO.setOutstandingMarketValue(new BigDecimal(akApiResponse.getValue()));
                    break;
                case "行业":
                    stockDetailDTO.setIndustry(akApiResponse.getValue());
                    break;
                case "上市时间":
                    stockDetailDTO.setListingDate(akApiResponse.getValue());
                    break;
                case "股票代码":
                    stockDetailDTO.setCode(akApiResponse.getValue());
                    break;
                case "股票简称":
                    stockDetailDTO.setName(akApiResponse.getValue());
                    break;
                case "总股本":
                    stockDetailDTO.setTotalShares(new BigDecimal(akApiResponse.getValue()));
                    break;
                case "流通股":
                    stockDetailDTO.setOutstandingShares(new BigDecimal(akApiResponse.getValue()));
                    break;
                default:
                    break;
            }
        }
        return stockDetailDTO;
    }
}
