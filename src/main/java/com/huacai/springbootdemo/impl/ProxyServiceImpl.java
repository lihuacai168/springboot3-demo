package com.huacai.springbootdemo.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huacai.springbootdemo.util.SimpleHttpClient;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ProxyServiceImpl {
     private final ObjectMapper objectMapper;



    public ProxyServiceImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public JsonNode post(String url, Object  body, Map<String, String> headers) throws JsonProcessingException {
        val resp = SimpleHttpClient.post(url, body, headers);
        return objectMapper.readTree(resp);
    }
}
