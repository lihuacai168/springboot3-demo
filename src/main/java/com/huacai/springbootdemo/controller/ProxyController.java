package com.huacai.springbootdemo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.huacai.springbootdemo.core.Result;
import com.huacai.springbootdemo.dto.req.ProxyInDto;
import com.huacai.springbootdemo.impl.ProxyServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ProxyController {
    @Resource
    private ProxyServiceImpl proxyService;

    @PostMapping("/proxy/post")
    public Result<JsonNode> post(@RequestBody ProxyInDto reqBody) {
        try {
            return Result.OK(proxyService.post(reqBody.getUrl(), reqBody.getBody(), reqBody.getHeaders()));

        } catch (Exception e) {
            return Result.Error(e.getLocalizedMessage());
        }
    }
}
