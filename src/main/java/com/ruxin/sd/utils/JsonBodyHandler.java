package com.ruxin.sd.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Slf4j
public class JsonBodyHandler<T> implements HttpResponse.BodyHandler<T> {

    private final Class<T> responseType;
    private final TypeReference<T> typeReference;
    private final ObjectMapper objectMapper;

    public JsonBodyHandler(Class<T> responseType) {
        this.responseType = responseType;
        this.typeReference = null;
        this.objectMapper = new ObjectMapper();
    }

    public JsonBodyHandler(TypeReference<T> typeReference) {
        this.typeReference = typeReference;
        this.responseType = null;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public HttpResponse.BodySubscriber<T> apply(HttpResponse.ResponseInfo responseInfo) {
        return HttpResponse.BodySubscribers.mapping(
                HttpResponse.BodySubscribers.ofString(StandardCharsets.UTF_8),
                responseBody -> {
                    if (log.isDebugEnabled()) {
                        log.debug("Response type:{}, body: {}", responseType, responseBody);
                    }
                    if (typeReference != null) {
                        try {
                            return objectMapper.readValue(responseBody, typeReference);
                        } catch (JsonProcessingException e) {
                            log.error("Failed to parse response body,[{}]", responseBody, e);
                            throw new RuntimeException(e);
                        }
                    }
                    if (responseType != null) {
                        try {
                            return objectMapper.readValue(responseBody, responseType);
                        } catch (JsonProcessingException e) {
                            log.error("Failed to parse response body,[{}]", responseBody, e);
                            throw new RuntimeException(e);
                        }
                    }
                    throw new RuntimeException("responseType and typeReference are both null");
                });
    }
}
