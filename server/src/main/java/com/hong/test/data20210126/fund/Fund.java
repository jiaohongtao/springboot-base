package com.hong.test.data20210126.fund;

import lombok.Data;

/**
 * @author jiaohongtao
 * @version 1.0
 * @since 2021年03月12日
 */
@Data
public class Fund {
    /**
     * 基金代码
     **/
    private String code;
    /**
     * 基金名称
     **/
    private String name;
    /**
     * 基金类型
     **/
    private String type;
    /**
     * 净值时间
     **/
    private String date;
    /**
     * 基金净值
     **/
    private Double netValue;
    /**
     * 日增长率
     **/
    private Double dayRise;
    /**
     * 近1周
     **/
    private Double weekRise;
    /**
     * 近1个月
     **/
    private Double monthRise;
    /**
     * 近3个月
     **/
    private Double threeMonthsRise;
    /**
     * 近6个月
     **/
    private Double sixMonthsRise;
    /**
     * 近1年
     **/
    private Double oneYearRise;
    /**
     * 近2年
     **/
    private Double twoYearsRise;
    /**
     * 近3年
     **/
    private Double threeYearsRise;
    /**
     * 近1年
     **/
    private Double curYearRise;
    /**
     * 成立以来
     **/
    private Double historyRise;
    /**
     * 手续费
     **/
    private Double poundage;
    /**
     * 是否可以定投0no1ok
     **/
    private Integer dtStatus;
    /**
     * 交易状态
     **/
    private Integer dealStatus;
    /**
     * 行文本数据
     **/
    private String rowData;

}