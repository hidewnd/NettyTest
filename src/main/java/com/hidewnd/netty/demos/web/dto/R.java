package com.hidewnd.netty.demos.web.dto;

import lombok.Data;

@Data
public class R<T> {

    private Integer code;
    private Boolean success;
    private String message;
    private T data;

    public R(Integer code, String message) {
        this.code = code;
        this.message = message;
        this.success = Boolean.TRUE;
    }

    public R(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.success = Boolean.TRUE;
        this.data  = data;
    }

    public static R<String> success(String message) {
        return new R<>(1000, message);
    }

    public static <T> R<T> success(String message, T data) {
        return new R<>(1000,  message, data);
    }
}
