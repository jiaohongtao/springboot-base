package com.hong;

import com.hong.bean.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SpringbootHtmlApplicationTests {

	@Autowired
	Person person;

	@Test
	public void contextLoads() {
		System.out.println(person.getName() + ":" + person.getAge());
	}

}
