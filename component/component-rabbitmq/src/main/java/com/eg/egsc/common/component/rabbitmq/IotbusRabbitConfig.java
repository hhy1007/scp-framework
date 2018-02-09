/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.common.component.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 物联网总线mq的配置
 * 
 * @author songjie
 * @since 2018年1月17日
 */
@Configuration
public class IotbusRabbitConfig extends RabbitConfig {
	@Autowired
	private Properties rabbitProperties;

	@Autowired
	private Properties properties;

	private static final Logger logger = LoggerFactory
			.getLogger(IotbusRabbitConfig.class);

	/**
	 * 连接工厂类
	 * 
	 * @return ConnectionFactory
	 */
	@Bean(name = "iotbusConnectionFactory")
	public ConnectionFactory iotbusConnectionFactory() {
		if (rabbitProperties.validIotbusQueue()) {
			return instanceConnectionFactory(rabbitProperties.getIotbusHost(),
					Integer.valueOf(rabbitProperties.getIotbusPort()),
					rabbitProperties.getIotbusUsername(),
					rabbitProperties.getIotbusPassword(),
					rabbitProperties.getIotVirtualHost());
		}
		return new CachingConnectionFactory();
	}

	/**
	 * 实例化rabbitTemplate
	 * 
	 * @param connectionFactory
	 * @return RabbitTemplate
	 */
	@Bean(name = "iotbusRabbitTemplate")
	public RabbitTemplate iotbusRabbitTemplate(
			@Qualifier("iotbusConnectionFactory") ConnectionFactory connectionFactory) {
		return instanceRabbitTemplate(connectionFactory);
	}

	/**
	 * 实例化connectionFactory
	 * 
	 * @param connectionFactory
	 * @return SimpleRabbitListenerContainerFactory
	 */
	@Bean(name = "iotbusFactory")
	public SimpleRabbitListenerContainerFactory iotbusFactory(
			@Qualifier("iotbusConnectionFactory") ConnectionFactory connectionFactory) {
		return instanceFactory(connectionFactory);
	}

	/**
	 * 创建路由器
	 * 
	 * @param connectionFactory
	 * @return TopicExchange
	 */
	@Bean
	public TopicExchange iotbusTopicExchange(
			@Qualifier("iotbusConnectionFactory") CachingConnectionFactory connectionFactory) {
		if ("true".equals(properties.getIotbusExchange())) {
			try {
				Connection connection = connectionFactory
						.getRabbitConnectionFactory().newConnection();
				Channel channel = connection.createChannel();
				channel.exchangeDeclare(Properties.TOPIC_EXCHANGE, "topic",
						true);
				logger.info("iotbus exchangeDeclare成功：TOPIC.EXCHANGE");
			} catch (IOException e) {
				logger.info("iotbus exchangeDeclare失败：" + e.getMessage());
			} catch (TimeoutException e) {
				logger.info("iotbus exchangeDeclare失败，访问超时：" + e.getMessage());
			}
		}
		return new TopicExchange(Properties.TOPIC_EXCHANGE);
	}
}
