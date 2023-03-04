package com.hong;

import com.hong.bean.Person;
import com.hong.test.company.esDir.EsService;
import com.hong.test.company.esDir.UserDocument;
import com.hong.util.common.PropertiesUtil;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author jiaohongtao
 * @version 1.0
 * @since 2020年11月05日
 */
// @SpringBootTest
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class Test1 {

    @Autowired
    PropertiesUtil propertiesUtil;

    @Autowired
    Person person;

    @Test
    public void test1() {
        propertiesUtil.getName();
        System.out.println(person);
    }


    @Autowired
    EsService esService;

    @Test
    public void testEs() throws Exception {

        // System.out.println("结果：" + createIndex());
        // 66017379-61c0-49a7-b02e-08a1be4cd3b5
        // System.out.println("结果：" + createData());
        /*GetIndexResponse index = getIndex();
        System.out.println(index);*/
        String id = "66017379-61c0-49a7-b02e-08a1be4cd3b5";
        UserDocument data = getData(id);
        System.out.println(data);
    }

    public UserDocument getUser() throws Exception {
        return esService.getUserDocument("1234");
    }

    public boolean createIndex() throws Exception {
        // index name 必须小写
        return esService.createUserIndex("cs001");
    }

    public GetIndexResponse getIndex() throws Exception {
        // index name 必须小写
        return esService.getUserIndex("cs001");
    }

    public boolean createData() throws Exception {
        return esService.createUserDocument(new UserDocument());
    }

    public UserDocument getData(String id) throws Exception {
        return esService.getUserDocument(id);
    }


}
