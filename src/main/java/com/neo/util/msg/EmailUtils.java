package com.neo.util.msg;


import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 发送电子邮件工具类
 *
 * @author 27351
 */
@Component
public class EmailUtils {

	private final static Logger logger = LoggerFactory.getLogger(EmailUtils.class);

	@Value("${msg.email.fromAddress}")
	private String fromAddress;

	@Value("${msg.email.fromPassword}")
	private String fromPassword;

	@Value("${msg.email.fromHostName}")
	private String fromHostName;

	@Value("${msg.email.sslOnConnect}")
	private String sslOnConnect;

	@Value("${msg.email.sslSmtpPort}")
	private String sslSmtpPort;

	/**
	 * 发送邮件
	 *
	 * @param toAddress 接收地址
	 * @param subject   标题
	 * @param content   内容
	 * @return
	 */
	public void send(String toAddress, String subject, String content) {
		send(fromAddress, fromPassword, fromHostName, sslOnConnect, sslSmtpPort, toAddress, subject, content);
	}


	/**
	 * 发送邮件
	 *
	 * @param fromAddress  发送地址
	 * @param fromPassword 发送密码
	 * @param fromHostName 发送host
	 * @param sslOnConnect ssl
	 * @param sslSmtpPort  ssl端口
	 * @param toAddress    接收地址
	 * @param subject      标题
	 * @param content      内容
	 * @return
	 */
	public void send(String fromAddress, String fromPassword, String fromHostName,
	                 String sslOnConnect, String sslSmtpPort, String toAddress, String subject, String content) {
		try {
			HtmlEmail htmlEmail = new HtmlEmail();
			// 发送地址
			htmlEmail.setFrom(fromAddress);
			// 密码校验
			htmlEmail.setAuthentication(fromAddress, fromPassword);
			// 发送服务器协议
			htmlEmail.setHostName(fromHostName);

			// SSL
			if ("true".equals(sslOnConnect)) {
				htmlEmail.setSSLOnConnect(true);
				htmlEmail.setSslSmtpPort(sslSmtpPort);
			}

			// 接收地址
			htmlEmail.addTo(toAddress);

			// 标题
			htmlEmail.setSubject(subject);
			// 内容
			htmlEmail.setMsg(content);

			// 其他信息
			htmlEmail.setCharset("utf-8");

			// 发送
			htmlEmail.send();
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
	}

}