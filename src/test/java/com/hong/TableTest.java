package com.hong;

import cn.hutool.json.JSONUtil;
import com.hong.bean.TestTable;
import com.hong.dao.TableMapper;
import com.mybatisflex.core.query.QueryWrapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.hong.bean.table.TestTableTableDef.TEST_TABLE;

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
        QueryWrapper query = QueryWrapper.create().select().where(TEST_TABLE.ID.eq(1));
        List<TestTable> all = mapper.selectListByQuery(query);
        // List<TestTable> all = mapper.selectAll();
        all.forEach(table -> System.out.println(JSONUtil.toJsonStr(table)));
    }
}
