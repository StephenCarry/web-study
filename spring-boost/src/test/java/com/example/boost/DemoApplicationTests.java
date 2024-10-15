package com.example.boost;

import com.example.boost.exeutor.service.ServiceExecutor;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ContextConfiguration(locations = "classpath*:bean.xml")
class DemoApplicationTests {
	@Autowired
	private ServiceExecutor serviceExecutor;

	@Value("${spring.datasource.username}")
	private String username;

	@Test
	public void springRun() {
		System.out.println("username is: " + username);
		serviceExecutor.execute();
	}

}
