/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.common.component.rabbitmq;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.util.StringUtils;

/**
 * rabbit配置基类
 * 
 * @author songjie
 * @since 2018年1月17日
 */
public class RabbitConfig {
	/**
	 * 实例化CachingConnectionFactory
	 * 
	 * @param host
	 * @param port
	 * @param username
	 * @param password
	 * @param virtualHost
	 * @return CachingConnectionFactory
	 */
	public CachingConnectionFactory instanceConnectionFactory(String host,
			int port, String username, String password, String virtualHost) {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
		connectionFactory.setHost(host);
		connectionFactory.setPort(port);
		connectionFactory.setUsername(username);
		connectionFactory.setPassword(password);
		if (!StringUtils.isEmpty(virtualHost)) {
			connectionFactory.setVirtualHost(virtualHost);
		}
		return connectionFactory;
	}

	/**
	 * 实例化RabbitTemplate
	 * 
	 * @param connectionFactory
	 * @return RabbitTemplate
	 */
	public RabbitTemplate instanceRabbitTemplate(
			ConnectionFactory connectionFactory) {
		RabbitTemplate template = new RabbitTemplate(connectionFactory);
		template.setMessageConverter(new Jackson2JsonMessageConverter());
		return template;
	}

	/**
	 * 实例化SimpleRabbitListenerContainerFactory
	 * 
	 * @param connectionFactory
	 * @return SimpleRabbitListenerContainerFactory
	 */
	public SimpleRabbitListenerContainerFactory instanceFactory(
			ConnectionFactory connectionFactory) {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setConnectionFactory(connectionFactory);
		factory.setMessageConverter(new Jackson2JsonMessageConverter());
		return factory;
	}
}
