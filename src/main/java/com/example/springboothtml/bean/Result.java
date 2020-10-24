package com.example.springboothtml.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 返回结果
 *
 * @author jiaohongtao
 * @version 1.0
 * @since 2020/10/24 16:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private boolean success;
    private Object data;
    private String message;
    // private int code;

    public static Result success(Object data) {
        return new Result(true, data, null);
    }

    public static Result success(Object data, String message) {
        return new Result(true, data, message);
    }

    public static Result failed(String message) {
        return new Result(false, null, message);
    }

    public static Result failed(Object data, String message) {
        return new Result(false, data, message);
    }
}
