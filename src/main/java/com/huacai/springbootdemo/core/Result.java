package com.huacai.springbootdemo.core;

import lombok.Data;

@Data
public class Result<T> {
    private T data;
    private String message;
    private Boolean ok;

    private Result(T data, String message, Boolean ok) {
        this.data = data;
        this.message = message;
        this.ok = ok;
    }

    public static <T> Result<T> OK(T data) {
        return new Result<T>(data, "Success", true);
    }

    public static <T> Result<T> Error(String message) {
        return new Result<T>(null, message, false);
    }
}
