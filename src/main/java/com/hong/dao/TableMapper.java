package com.hong.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hong.bean.Table;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author jiaohongtao
 * @version 1.0.0
 * @since 2022/07/18
 */
@Mapper
public interface TableMapper extends BaseMapper<Table> {

    @Select("select * from test_table ")
    List<Table> all();

}
