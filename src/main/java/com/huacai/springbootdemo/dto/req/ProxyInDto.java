package com.huacai.springbootdemo.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Map;

@Data
public class ProxyInDto {
    @NotNull
    @NotBlank
    String url;

    Object body;

    Map<String, String> headers;
}
