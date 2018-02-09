/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.eg.egsc.common.component.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 日志服务生产消息类
 * 
 * @author songjie
 * @since 2018年1月17日
 */
public class LogserviceSender extends BaseSender {
	@Autowired
	private AmqpTemplate logserviceRabbitTemplate;

	@Autowired
	private ConnectionFactory logserviceConnectionFactory;

	/**
	 * 获取 AmqpTemplate
	 * 
	 * @return AmqpTemplate
	 */
	@Override
	public AmqpTemplate getRabbitTemplate() {
		return logserviceRabbitTemplate;
	}

	/**
	 * 获取 ConnectionFactory
	 * 
	 * @return ConnectionFactory
	 */
	@Override
	public ConnectionFactory getConnectionFactory() {
		return logserviceConnectionFactory;
	}
}
