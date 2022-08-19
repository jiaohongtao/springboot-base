package com.hong;

import cn.hutool.json.JSONUtil;
import com.hong.bean.Table;
import com.hong.dao.TableMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author jiaohongtao
 * @version 1.0
 * @since 2020年11月05日
 */
// @SpringBootTest
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TableTest {

    @Autowired
    private TableMapper mapper;

    @Test
    public void test() {
        List<Table> all = mapper.all();
        all.forEach(table -> System.out.println(JSONUtil.toJsonStr(table)));
    }
}
