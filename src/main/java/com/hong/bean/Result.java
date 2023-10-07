package com.hong.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
public class Result<T> {
    private boolean success;
    private T data;
    private String message;
    // private int code;

    public static <T> Result<T> success(T data) {
        return success(data, "操作成功");
    }

    public static <T> Result<T> success(T data, String message) {
        return new Result<>(true, data, message);
    }

    public static <T> Result<T> failed(String message) {
        return failed(null, message);
    }

    public static <T> Result<T> failed(T data, String message) {
        return new Result<>(false, data, message);
    }

    public boolean isFailed() {
        return !isSuccess();
    }
}
