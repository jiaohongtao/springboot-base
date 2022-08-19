package com.hong.exception;

/**
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2022/08/19
 */
public class BusinessException extends BaseException {

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException() {
        super();
    }
}
