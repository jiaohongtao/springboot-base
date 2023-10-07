package com.hong.study.design_patterns.state;

/**
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2023/09/22
 */
public class TransportState implements LogisticsState {
    @Override
    public void doAction(JdLogistics context) {
        System.out.println("商品正在运往天津分发中心");
    }
}
