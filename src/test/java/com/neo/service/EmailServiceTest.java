package com.neo.service;

import com.neo.util.msg.EmailUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Huangcz
 * @date 2018-11-27 15:08
 * @desc xxx
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailServiceTest {


	@Autowired
	private EmailUtils emailUtils;

	@Test
	public void test() {
		String toAddress = "755635051@qq.com";
//		String toAddress = "273514614@qq.com";
		String subject = "test！！！！！！！！！";
//		String content = "test！！！！！！！！！ 刚 ！！！！  娃  ！！e！r！！  子 ！！！！！ \n"+
//							"test！！！！！！！！！ 刚 ！！！！  娃  ！e！r！！！  子 ！！！！！  \n";
		String content = "test！！！！！！！！！  ！！！！    ！！e！r！！   ！！！！！ \n"+
				"test！！！！！！！！！  ！！！！    ！e！r！！！   ！！！！！  \n";
		emailUtils.send(toAddress,subject,content);
	}
}
