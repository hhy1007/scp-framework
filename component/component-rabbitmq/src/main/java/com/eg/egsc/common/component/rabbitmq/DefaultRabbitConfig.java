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
 * default rabbitmq 配置类
 * 
 * @author songjie
 * @since 2018年1月17日
 */
@Configuration
public class DefaultRabbitConfig extends RabbitConfig {
	@Autowired
	private Properties rabbitProperties;

	@Autowired
	private Properties properties;

	private static final Logger logger = LoggerFactory
			.getLogger(DefaultRabbitConfig.class);

	/**
	 * 连接工厂类
	 * 
	 * @return ConnectionFactory
	 */
	@Bean(name = "connectionFactory")
	public ConnectionFactory connectionFactory() {
		if (rabbitProperties.validQueue()) {
			return instanceConnectionFactory(rabbitProperties.getHost(),
					Integer.valueOf(rabbitProperties.getPort()),
					rabbitProperties.getUsername(),
					rabbitProperties.getPassword(),
					rabbitProperties.getVirtualHost());
		}
		return new CachingConnectionFactory();
	}

	/**
	 * 实例化rabbitTemplate
	 */
	@Bean(name = "rabbitTemplate")
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		return instanceRabbitTemplate(connectionFactory);
	}

	/**
	 * rlcFactory
	 * 
	 * @param connectionFactory
	 * @return SimpleRabbitListenerContainerFactory
	 */
	@Bean(name = "rlcFactory")
	public SimpleRabbitListenerContainerFactory rlcFactory(
			ConnectionFactory connectionFactory) {
		return instanceFactory(connectionFactory);
	}

	/**
	 * 创建路由器
	 * 
	 * @param connectionFactory
	 * @return TopicExchange
	 */
	@Bean
	public TopicExchange topicExchange(
			@Qualifier("connectionFactory") CachingConnectionFactory connectionFactory) {
		if ("true".equals(properties.getExchange())) {
			// 默认为false，不创建exchange
			try {
				Connection connection = connectionFactory
						.getRabbitConnectionFactory().newConnection();
				Channel channel = connection.createChannel();
				channel.exchangeDeclare(Properties.TOPIC_EXCHANGE, "topic",
						true);
				logger.info("exchangeDeclare成功：TOPIC.EXCHANGE");
			} catch (IOException e) {
				logger.info("exchangeDeclare失败：" + e.getMessage());
			} catch (TimeoutException e) {
				logger.info("exchangeDeclare失败，访问超时：" + e.getMessage());
			}
		}
		return new TopicExchange(Properties.TOPIC_EXCHANGE);
	}

}
