package com.ruxin.sd.utils;

import java.util.Map;

public class HttpUtils {

  public static String buildUrlWithQueryParams(String baseUrl, Map<String, String> queryParams) {
    StringBuilder urlBuilder = new StringBuilder(baseUrl);
    if (!queryParams.isEmpty()) {
      urlBuilder.append("?");
      for (Map.Entry<String, String> entry : queryParams.entrySet()) {
        urlBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
      }
      // 移除最后多余的 '&' 字符
      urlBuilder.deleteCharAt(urlBuilder.length() - 1);
    }
    return urlBuilder.toString();
  }
}
