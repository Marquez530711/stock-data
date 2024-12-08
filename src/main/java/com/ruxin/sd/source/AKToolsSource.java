package com.ruxin.sd.source;

import com.fasterxml.jackson.core.type.TypeReference;
import com.ruxin.sd.source.entity.AKApiResponse;
import com.ruxin.sd.source.entity.StockDetailDTO;
import com.ruxin.sd.source.entity.StockInfoDTO;
import com.ruxin.sd.source.entity.StockPriceDTO;
import com.ruxin.sd.utils.HttpUtils;
import com.ruxin.sd.utils.JsonBodyHandler;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class AKToolsSource implements BaseSource {

    private final String endpoint;
    private final HttpClient httpClient;

    public AKToolsSource() {
        this.endpoint = "http://localhost:8080/api/public";
        this.httpClient = HttpClient.newHttpClient();
    }

    @Override
    public List<StockInfoDTO> getAllAStockInfo() {
        String url = endpoint + "/stock_info_a_code_name";
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(URI.create(url)).header(HttpHeaders.CONTENT_TYPE, "application/json").GET().build();
        try {
            HttpResponse<List<StockInfoDTO>> response = httpClient.send(httpRequest, new JsonBodyHandler<>(new TypeReference<>() {
            }));
            if (response.statusCode() != 200) {
                throw new RuntimeException("Failed to get all A stock info");
            }
            log.info("Get all A stock info response body: {}", response.body());
            return response.body();
        } catch (Exception e) {
            log.error("Failed to get all A stock info", e);
        }
        return List.of();
    }

    @Override
    public StockDetailDTO getAStockDetail(String code) {
        Map<String, String> params = Map.of("symbol", code);
        String url = HttpUtils.buildUrlWithQueryParams(endpoint + "/stock_individual_info_em", params);
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).header(HttpHeaders.CONTENT_TYPE, "application/json").GET().build();
        try {
            log.info("Sending request to get A stock detail: url {}", url);
            HttpResponse<List<AKApiResponse>> response = httpClient.send(request,
                    new JsonBodyHandler<>(new TypeReference<>() {
                    }));
            if (response.statusCode() != 200) {
                throw new RuntimeException("Failed to get A stock detail");
            }
            log.info("Get A stock detail response body: {}", response.body());
            return StockDetailDTO.from(response.body());
        } catch (IOException | InterruptedException e) {
            log.error("Failed to get A stock detail", e);
        }
        return new StockDetailDTO();
    }

    @Override
    public List<StockPriceDTO> getAStockPrice(String code, String startDate, String endDate, String adjust) {
        // 参数 symbol="600153", period="daily", start_date="20241201", end_date='20241228', adjust="qfq"
        Map<String, String> params = Map.of("symbol", code, "period", "daily", "start_date", startDate, "end_date", endDate, "adjust", adjust);
        String url = HttpUtils.buildUrlWithQueryParams(endpoint + "/stock_zh_a_hist", params);
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).header(HttpHeaders.CONTENT_TYPE, "application/json").GET().build();
        try {
            log.info("Sending request to get A stock price: url {}", url);
            HttpResponse<List<StockPriceDTO>> response = httpClient.send(request, new JsonBodyHandler<>(new TypeReference<>() {
            }));
            if (response.statusCode() != 200) {
                throw new RuntimeException("Failed to get A stock price");
            }
            log.info("Get A stock price response body: {}", response.body());
            return response.body();
        } catch (IOException | InterruptedException e) {
            log.error("Failed to get A stock price", e);
        }
        return List.of();
    }

}
