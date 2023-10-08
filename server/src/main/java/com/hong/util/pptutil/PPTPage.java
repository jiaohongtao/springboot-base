package com.hong.util.pptutil;

/**
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2022/09/30
 */
public enum PPTPage {
    weekContent(3),
    weekSummary(11),
    codePic(4),
    chandao(5),
    lastWeek(13);

    private final Integer code;

    PPTPage(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
