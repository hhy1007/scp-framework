/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.common.component.rabbitmq;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 日志服务mq的配置
 * 
 * @author songjie
 * @since 2018年1月17日
 */
@Configuration
public class LogserviceRabbitConfig extends RabbitConfig {
	@Autowired
	private Properties rabbitProperties;

	/**
	 * 连接工厂类
	 * 
	 * @return ConnectionFactory
	 */
	@Bean(name = "logserviceConnectionFactory")
	public ConnectionFactory connectionFactory() {
		if (rabbitProperties.validLogQueue()) {
			return instanceConnectionFactory(rabbitProperties.getLogHost(),
					Integer.valueOf(rabbitProperties.getLogPort()),
					rabbitProperties.getLogUsername(),
					rabbitProperties.getLogPassword(),
					rabbitProperties.getLogVirtualHost());
		}
		return new CachingConnectionFactory();
	}

	/**
	 * 实例化rabbitTemplate
	 * 
	 * @param connectionFactory
	 * @return RabbitTemplate
	 */
	@Bean(name = "logserviceRabbitTemplate")
	public RabbitTemplate iotbusRabbitTemplate(
			@Qualifier("logserviceConnectionFactory") ConnectionFactory connectionFactory) {
		return instanceRabbitTemplate(connectionFactory);
	}

	/**
	 * 实例化connectionFactory
	 * 
	 * @param connectionFactory
	 * @return SimpleRabbitListenerContainerFactory
	 */
	@Bean(name = "logserviceFactory")
	public SimpleRabbitListenerContainerFactory iotbusFactory(
			@Qualifier("logserviceConnectionFactory") ConnectionFactory connectionFactory) {
		return instanceFactory(connectionFactory);
	}

}
