package com.hong.bean;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;

/**
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2022/08/19
 */
@Data
@Table("test_table")
public class TestTable {

    @Id(keyType = KeyType.Auto)
    private Long id;
    private String name;
}
