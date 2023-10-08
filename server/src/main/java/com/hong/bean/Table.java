package com.hong.bean;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2022/08/19
 */
@Data
@TableName("test_table")
public class Table {

    private Long id;
    private String name;
}
