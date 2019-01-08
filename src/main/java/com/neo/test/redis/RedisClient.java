package com.neo.test.redis;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author Huangcz
 * @date 2018-11-30 15:43
 * @desc redis客户端
 */
public class RedisClient {

	// 往外写数据
	OutputStream outputStream;
	// 读数据
	InputStream inputStream;

	public RedisClient(String host, int port) throws Exception {
		Socket socket = new Socket(host,port);

		outputStream = socket.getOutputStream();

		inputStream = socket.getInputStream();

	}


	public void set(String key , String value) throws Exception{
		// 1. 组装数据
		StringBuffer data = new StringBuffer();

		data.append("*3").append("\r\n");
		data.append("$3").append("\r\n");
		data.append("SET").append("\r\n");
		data.append("$").append(key.getBytes().length).append("\r\n");
		data.append(key).append("\r\n");
		data.append("$").append(value.getBytes().length).append("\r\n");
		data.append(value).append("\r\n");

		// 2. 指令数据发送给redis客户端
		outputStream.write(data.toString().getBytes());
		System.out.println("发送成功");
		System.out.println(data);

		// 3. 接受服务端响应
		byte[] response = new byte[1024];
		inputStream.read(response);

		System.out.println("接收到的响应：" + new String(response));

	}

	public static void main(String[] args) throws Exception {
		RedisClient redisClient = new RedisClient("127.0.0.1",6379);
		redisClient.set("aaa","123456789");
	}


}
