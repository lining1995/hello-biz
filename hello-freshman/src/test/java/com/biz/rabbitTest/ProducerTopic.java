package com.biz.rabbitTest;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ProducerTopic {
	public static void main(String[] args) throws IOException, TimeoutException {
		Connection connection = null;
		Channel channel = null;
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("localhost");
			factory.setPort(5672);
			factory.setUsername("rabbitmq_producer");
			factory.setPassword("123456");
			factory.setVirtualHost("test_host");

			// 创建与RabbitMQ服务器的TCP连接
			connection = factory.newConnection();
			channel = connection.createChannel();
			channel.exchangeDeclare("topicExchange", "topic");
			channel.queueDeclare("topicQueue", true, false, false, null);
			channel.queueBind("topicQueue", "topicExchange", "topicMessage.*");

			String message = "First topic Message";

			channel.basicPublish("topicExchange", "topicMessage.one", null, message.getBytes());
			System.out.println("Send topic Message is:'" + message + "'");

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (channel != null) {
				channel.close();
			}
			if (connection != null) {
				connection.close();
			}
		}
	}
}