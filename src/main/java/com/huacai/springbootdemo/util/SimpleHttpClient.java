package com.huacai.springbootdemo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import org.springframework.http.*;


@Component
public class SimpleHttpClient {

    private final RestTemplate restTemplate;

    // 使用@Autowired注解使得Spring自动注入RestTemplate实例
    @Autowired
    public SimpleHttpClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // 将getDefaultHeaders方法转换为非静态方法
    private HttpHeaders getDefaultHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("token", "feign");
        return httpHeaders;
    }

    // 将prepareHeaders方法转换为非静态方法
    private HttpHeaders prepareHeaders(Map<String, String> headers) {
        HttpHeaders httpHeaders = getDefaultHeaders();
        if (headers != null) {
            headers.forEach((key, value) ->
                httpHeaders.add(key, value)
            );
        }
        return httpHeaders;
    }

    // 将所有使用restTemplate的方法转换为非静态
    public String get(String url, Map<String, String> headers) {
        HttpHeaders httpHeaders = prepareHeaders(headers);
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return response.getBody();
    }

    public String post(String url, Object body, Map<String, String> headers) {
        HttpHeaders httpHeaders = prepareHeaders(headers);
        HttpEntity<Object> entity = new HttpEntity<>(body, httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        return response.getBody();
    }

    public <T> T post(String url, Object body, Map<String, String> headers, Class<T> responseType) {
        HttpHeaders httpHeaders = prepareHeaders(headers);
        HttpEntity<Object> entity = new HttpEntity<>(body, httpHeaders);
        ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.POST, entity, responseType);
        return response.getBody();
    }

    public String post(String url, Object body) {
        // 注意以下方法现在需用this来调用post方法，而不是SimpleHttpClient.post(...)
        return this.post(url, body, null);
    }
}