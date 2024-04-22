package com.huacai.springbootdemo.util;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import org.springframework.http.*;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


@Component
public class SimpleHttpClient {

    private static final RestTemplate restTemplate = new RestTemplate();

    private static HttpHeaders getDefaultHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.set("token", "feign"); // Adding token: feign
        return httpHeaders;
    }

    public static String get(String url, Map<String, String> headers) {
        HttpHeaders httpHeaders = getDefaultHeaders();
        if (headers != null) {
            MultiValueMap<String, String> multiHeaders = new LinkedMultiValueMap<>();
            headers.forEach(multiHeaders::add);
            httpHeaders.addAll(multiHeaders);
        }
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        return response.getBody();
    }

    public static String post(String url, Object body, Map<String, String> headers) {
        HttpHeaders httpHeaders = getDefaultHeaders();
        if (headers != null) {
            MultiValueMap<String, String> multiHeaders = new LinkedMultiValueMap<>();
            headers.forEach(multiHeaders::add);
            httpHeaders.addAll(multiHeaders);
        }
        HttpEntity<Object> entity = new HttpEntity<>(body, httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        return response.getBody();
    }

    public static String post(String url, Object body) {
        return post(url, body, null); // Call the overloaded method with null headers
    }
}
